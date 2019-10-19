import { getRemoteDiagnose,getRemoteDiagnoseNameAll,updateremoteDiagnose} from '@/services/remoteDiagnose';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';
export default {
    namespace: 'remoteDiagnose',

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
       sysDiaRow:{
           status:201,
           ok: false,
           msg: "",
           data:[],
       },

       
       sysDiaRowName:{
        status:201,
        ok: false,
        msg: "",
        data:[],
    },
       treeData:[],
         //抽屉页面展示状态
         drawerVisible:false,
   },


   effects: {

    //分页查询
    *fetch( { payload }, { select, call, put }) {
        //从cookie获取limit的值，无值从utilsConfig获取
        if(!cookie.load('limit')){
            cookie.save('limit',yield select(state => state.remoteDiagnose.data.pagination.pageSize));
        }
        payload ={
            ...payload,
            pageSize: +cookie.load('limit'),
            name: payload.name?payload.name:'',
            tel: payload.tel?payload.tel:'',
        }
        const formData = formatData(payload);
        const response = yield call(getConsultation,formData);
        yield put({
            type: 'save',
            payload: {
                ...response,
                ...payload,
            },
        });
    },
    
    //获取菜单信息
    *getDiaMenus( {payload}, { call, put }) {
      
      const formData = formatData(payload);
      const response = yield call(getSysMenuAll,formData);
      yield put({
          type: 'sysMenuRow',
          payload: response,
      });
  },
  *getRemoteDiagnose( {payload}, { call, put }) {
      
      const formData = formatData(payload);
      const response = yield call(getRemoteDiagnose,formData);
      yield put({
          type: 'sysDiaRow',
          payload: response,
      });
  },

  
  //查询所有专家名称
  *getRemoteDiagnoseNameAll( {payload}, { call, put }) {
      
    const formData = formatData(payload);
    const response = yield call(getRemoteDiagnoseNameAll,formData);
    yield put({
        type: 'sysDiaRowName',
        payload: response,
    });
},

 //修改
 *update( {payload,callback}, { select,call, put }) {

   
    const response = yield call(updateremoteDiagnose,payload);
    if(callback) callback(response);
    //刷新页面
    let queryCriteria = yield select(state => state.remoteDiagnose.queryCriteria);
    const current = yield select(state => state.remoteDiagnose.data.pagination.current);
    yield put({
        type: 'fetch',
        payload: {
            ...queryCriteria,
            pageCurrent: current,
            organizationId: 1,
        },
    });
},

  
},


reducers: {
            //清除查询条件
            clearQueryCriteria(state){
                return{
                    ...state,
                    queryCriteria:{},
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

              
         //保存后台查询到的菜单信息
         sysDiaRow(state,action){
        
     
        return {
            ...state,
            treeData:{
           ...action.payload.data
       }
            
            

        };
    },

    

       //查询所有所专家
       sysDiaRowName(state,action){
          
        return {
            ...state,
            sysDiaRowName:{
           ...action.payload
       }
            
            

        };
    },
    }
}