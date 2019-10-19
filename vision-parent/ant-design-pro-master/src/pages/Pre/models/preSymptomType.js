import {getPreSymptomTypeAll,findSymptomDesc,findAllObjectsList,findSymptomfindSymptomById,
        updateSymptom ,addPreSymptomType,deletePreSymptomType} from '@/services/PreSymptomTypeMenus';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';
export default {
    namespace: 'preSymptomType',
    state: {
        //选择框点击后存储数据
        selectedRows:[],
        //查询条件
        queryCriteria:{},
        data:{
            list:[],
            pagination:{
                current: 1,
                pageSize: 20,
                total: 0,
            },
            status:201,
            msg:"",
            ok:false,
        },
        //删除按钮不可用
        deleteDisabled: true,
        //选择框点击后存储id值
        selectedRowKeys:[],
        //查询行信息
        preRow:{
            status:201,
            ok: false,
            msg: "",
            data:[],
        },
         //保存症状处方查询行信息
         DescRow:{
            status:201,
            ok: false,
            msg: "",
            data:[],
        },

           //保存症状处方查询行信息
           DescRowById:{
            status:201,
            ok: false,
            msg: "",
            data:[],
        },

         //保存症状处方查询行信息,用于添加和修改
         savRow:{
            status:201,
            ok: false,
            msg: "",
            data:[],
        },

        //抽屉页面展示状态
        drawerVisible:false,
        treeList:[],
    },

    effects: {


              //分页查询
        *fetch( { payload }, { select, call, put }) {
            //从cookie获取limit的值，无值从utilsConfig获取
            if(!cookie.load('limit')){
                cookie.save('limit',yield select(state => state.preSymptomType.data.pagination.pageSize));
            }
            payload ={
                ...payload,
                pageSize: +cookie.load('limit'),
                title: payload.title?payload.title:'',
            }
            const formData = formatData(payload);
            const response = yield call(findAllObjectsList,formData);
            yield put({
                type: 'save',
                payload: {
                    ...response,
                    ...payload,
                },
            });
        },

         //修改
         *update( {payload,callback}, { select,call, put }) {
            const response = yield call(updateSymptom,payload);
            if(callback) callback(response);
            //刷新页面
            let queryCriteria = yield select(state => state.preSymptomType.queryCriteria);
            const current = yield select(state => state.preSymptomType.data.pagination.current);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: current,
                  
                },
            });
        },

            //添加
            *add( {payload,callback}, { select, call, put }) {
                const response = yield call(addPreSymptomType,payload);
                if(callback) callback(response);
                //刷新页面
                let queryCriteria = yield select(state => state.preSymptomType.queryCriteria);
                yield put({
                    type: 'fetch',
                    payload: {
                        ...queryCriteria,
                        pageCurrent: 1,
                       
                    },
                });
            },

           //查询所有症状数据用于导航菜单使用
           *getPreSymptomTypeAll( {payload}, { call, put }) {
          
            const formData = formatData(payload);
            const response = yield call(getPreSymptomTypeAll,formData);
            yield put({
                type: 'savePreRow',
                payload: response,
            });
        },
         //删除
         *remove({payload,callback}, { select, call, put }) {
            const response = yield call(deletePreSymptomType,payload);
            if(callback) callback(response);
            if(response.success){
                yield put({
                    type: 'selectRows',
                    payload: {
                    deleteDisabled:true,
                    selectedRows:[],
                    selectedRowKeys:[],
                    },
                });
            }
            //刷新页面
            let queryCriteria = yield select(state => state.preSymptomType.queryCriteria);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: 1,
                  
                },
            });
        },

         //更据症状id查询处方信息
         *findSymptomDesc( {payload}, { call, put }) {
           
            const formData = formatData(payload);
            const response = yield call(findSymptomDesc,formData);
            yield put({
                type: 'saveDescRow',
                payload: response,
            });
        },
        //更据id查询症状信息用于修改
        *findSymptomfindSymptomById( {payload}, { call, put }) {
           
            const formData = formatData(payload);
            const response = yield call(findSymptomfindSymptomById,formData);
            yield put({
                type: 'DescRowById',
                payload: response,
            });
        },
        

    },
    reducers: {
         
           //获取菜单名
           savePreRow(state,action){
            return {
                ...state,
                treeList:{
                    ...action.payload.data
                }
            };
        },

         //保存处方信息
         saveDescRow(state,action){
            return {
                ...state,
                DescRow:{
                    ...action.payload
                }
            };
        },

        //修改症状时，根据id查询数据
        DescRowById(state,action){
            return {
                ...state,
                DescRowById:{
                    ...action.payload
                }
            };
        },
        save(state, action) {
            return {
                ...state,
                queryCriteria:{
                    title: action.payload.title,
                },
                data:{
                    ...state.data,
                    list:action.payload.data.records,
                    pagination:{
                        current:action.payload.data.pageCurrent,
                        pageSize: action.payload.data.pageSize,
                        total: action.payload.data.rowCount,
                    },
                    status:action.payload.status,
                    msg:action.payload.msg,
                    ok:action.payload.ok,
                },
            };
        },
        //选择多选框
        selectRows(state,action){
            return {
                ...state,
                deleteDisabled:action.payload.deleteDisabled,
                selectedRows:action.payload.selectedRows,
                selectedRowKeys:action.payload.selectedRowKeys,
            };
        },
        //清除查询条件
        clearQueryCriteria(state){
            return{
                ...state,
                queryCriteria:{},
            };
        },

           //设置抽屉页面展示状态
           setDrawerVisible(state,action){
            return {
                ...state,
                drawerVisible:action.payload,
            };
        },

           //设置按id查询数据清空
           clearFeomData(state){
            return {
                ...state,
                DescRowById:{
                    status:201,
                    ok: false,
                    msg: "",
                    data:{},
                },
            };
        },
        
        
    }
}