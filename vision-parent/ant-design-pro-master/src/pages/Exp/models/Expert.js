import { getExpert,getExpertById,addExpert,updateExpert,deleteExpert } from '@/services/expert';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';
export default {
    namespace: 'expert',
    state: {
        //选择框点击后存储数据
        selectedRows:[],
          //查询条件
       queryCriteria:{},
         //删除按钮不可用
       deleteDisabled: true,
       //选择框点击后存储id值
       selectedRowKeys:[],
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
       //查询行信息
       expertRow:{
           status:201,
           ok: false,
           msg: "",
           data:{},
       },
       //抽屉页面展示状态
       drawerVisible:false,
      

   },

   effects: {
       //分页查询
       *fetch( { payload }, { select, call, put }) {
        
        
           
        //从cookie获取limit的值，无值从utilsConfig获取
        if(!cookie.load('limit')){
            
            cookie.save('limit',yield select(state => state.expert.data.pagination.pageSize));
        }
       
        payload ={
            ...payload,
            pageSize: +cookie.load('limit'),
            
        }
        const formData = formatData(payload);
        const response = yield call(getExpert,formData);
        yield put({
            type: 'save',
            payload: {
                ...response,
                ...payload,
            },
        });
    },

       //修改时根据id查询数据
       *getExpertById( {payload}, { call, put }) {
       
        const formData = formatData(payload);
 
        const response = yield call(getExpertById,formData);
        yield put({
            type: 'saveExpertRow',
            payload: response,
        });
    },

      //添加
      *add( {payload,callback}, { select, call, put }) {
        const response = yield call(addExpert,payload);
        if(callback) callback(response);
        //刷新页面
        let queryCriteria = yield select(state => state.expert.queryCriteria);
        yield put({
            type: 'fetch',
            payload: {
                ...queryCriteria,
                pageCurrent: 1,
            },
        });
    },

     //修改
     *update( {payload,callback}, { select,call, put }) {
     
        const response = yield call(updateExpert,payload);
        if(callback) callback(response);
        //刷新页面
        let queryCriteria = yield select(state => state.expert.queryCriteria);
        const current = yield select(state => state.expert.data.pagination.current);
        yield put({
            type: 'fetch',
            payload: {
                ...queryCriteria,
                pageCurrent: current,
               
            },
        });
    },

          //删除
          *remove({payload,callback}, { select, call, put }) {
              
            const response = yield call(deleteExpert,payload);
            if(callback) callback(response);
            if(response.success){
                yield put({
                    type: 'selectRows',
                    payload: {
                    deleteDisabled:true,
                    selectedRows:[],
                    selectedRowKeys:[]
                    },
                });
            }
            //刷新页面
            let queryCriteria = yield select(state => state.expert.queryCriteria);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: 1,
                    organizationId: 1,
                },
            });
        },

   },

   reducers: {
    save(state, action) {
            

        // console.log(state);
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
               //  ...action.payload,
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

       //设置按id查询数据清空
       clearFeomData(state){
        return {
            ...state,
            expertRow:{
                status:201,
                ok: false,
                msg: "",
                data:{},
            },
        };
    },

         //修改时根据id查询数据
         saveExpertRow(state,action){
            return {
                ...state,
                expertRow:{
                    ...action.payload,
                }
            };
        },
         //设置抽屉页面展示状态
         setDrawerVisible(state,action){
           
            return {
                
                ...state,
                drawerVisible:action.payload,
            };
        },

   }
}