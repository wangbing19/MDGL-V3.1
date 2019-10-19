import { getSysLog  } from '@/services/sysLog';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';
export default {
    namespace: 'sysLog',
    state: {
       
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
        cusRow:{
            status:201,
            ok: false,
            msg: "",
            data:{},
        },
       
    },

    effects: {
        //分页查询
        *fetch( { payload }, { select, call, put }) {
            //从cookie获取limit的值，无值从utilsConfig获取
         if(!cookie.load('limit')){
                cookie.save('limit',yield select(state => state.sysLog.data.pagination.pageSize));
           }
           
            // payload ={
            //     ...payload,
            //     pageSize: +cookie.load('limit'),
            //     name: payload.name?payload.name:'',
            //     tel: payload.tel?payload.tel:'',
            // }
            const formData = formatData(payload);
            const response = yield call(getSysLog,formData);
            yield put({
                type: 'save',
                payload: {
                    ...response,
                    ...payload,
                },
            });
        },
    },

    reducers: {
        save(state, action) {
          
            return {
                ...state,
                queryCriteria:{
                    name: action.payload.name,
                    tel: action.payload.tel,
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
        //修改时根据id查询数据
        saveCusRow(state,action){
            return {
                ...state,
                cusRow:{
                    ...action.payload,
                }
            };
        },
        //设置按id查询数据清空
        clearFeomData(state){
            return {
                ...state,
                cusRow:{
                    status:201,
                    ok: false,
                    msg: "",
                    data:{},
                },
            };
        },
    },
    
}