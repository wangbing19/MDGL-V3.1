import { getRemoteDiagnoseUser,getExpSymptomsDescribed,getAddExpSymptomsDescribed,
            getExpExpertReply,getExpRemoteDiagnoseOne,addremoteDiagnose,updateremoteDiagnose} from '@/services/remoteDiagnose';
import { getRemoteDiagnose,getRemoteDiagnoseNameAll} from '@/services/remoteDiagnose';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';
export default {
    namespace: 'remoteDiagnoseUser',

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
        //查询专家回复消息信息
        expertReplyRow:{
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

        sysDiaRowOne:{
            status:201,
            ok: false,
            msg: "",
            data:[],
        },
       
        //状态信息
        sysDiasStartRow: false,
       treeData:[],
         //诊断抽屉页面展示状态
         drawerVisible:false,
          //诊断订单增加，修改抽屉页面展示状态
          drawerVisibleUpdate:false,
   },


   effects: {

    //分页查询
    *fetch( { payload }, { select, call, put }) {

      
        //从cookie获取limit的值，无值从utilsConfig获取
        if(!cookie.load('limit')){
            cookie.save('limit',yield select(state => state.remoteDiagnoseUser.data.pagination.pageSize));
        }
        payload ={
            ...payload,
            pageSize: +cookie.load('limit'),
            name: payload.name?payload.name:'',
            tel: payload.tel?payload.tel:'',
        }
        const formData = formatData(payload);
        const response = yield call(getRemoteDiagnoseUser,formData);
        yield put({
            type: 'save',
            payload: {
                ...response,
                ...payload,
            },
        });
    },

    //用户页面订单添加
    *add( {payload,callback}, { select, call, put }) {
        const response = yield call(addremoteDiagnose,payload);
        if(callback) callback(response);
        //刷新页面
        let queryCriteria = yield select(state => state.remoteDiagnoseUser.queryCriteria);
        yield put({
            type: 'fetch',
            payload: {
                ...queryCriteria,
                pageCurrent: 1,
                organizationId: 1,
            },
        });
    },

     //修改
 *update( {payload,callback}, { select,call, put }) {

   
    const response = yield call(updateremoteDiagnose,payload);
    if(callback) callback(response);
    //刷新页面
    let queryCriteria = yield select(state => state.remoteDiagnoseUser.queryCriteria);
   
    yield put({
        type: 'fetch',
        payload: {
            ...queryCriteria,
            pageCurrent: current,
            organizationId: 1,
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

  

  *getExpSymptomsDescribed( {payload}, { call, put }) {
      
    const formData = formatData(payload);
    const response = yield call(getExpSymptomsDescribed,formData);
    yield put({
        type: 'sysDiaUserRow',
        payload: response,
    });

    
},



*getAddExpSymptomsDescribed( {payload}, { call, put }) {
      
    const formData = formatData(payload);
    const response = yield call(getAddExpSymptomsDescribed,formData);
    yield put({
        type: 'sysDiasStartRow',
        payload: response,
    });

    
},

//用户界面查询专家回复
*getExpExpertReply( {payload}, { call, put }) {
      
    const formData = formatData(payload);
    const response = yield call(getExpExpertReply,formData);
    yield put({
        type: 'ExpertReplyRow',
        payload: response,
    });

    
},


//用户界面订单查询
*getExpRemoteDiagnoseOne( {payload}, { call, put }) {
      
    const formData = formatData(payload);
    const response = yield call(getExpRemoteDiagnoseOne,formData);
    yield put({
        type: 'sysDiaRowOne',
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
            //清除查询条件
            clearQueryCriteria(state){
                return{
                    ...state,
                    queryCriteria:{},
                };
            },

              //清除查询条件
              clearSysDiaRowOne(state){
                return{
                    ...state,
                    sysDiaRowOne:{
                        status:201,
                        ok: false,
                        msg: "",
                        data:[],
                    },
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
            sysDiaRow:{
           ...action.payload.data
            }
                };
            },
        



           

             //保存后台查询到的菜单信息
             sysDiaRowOne(state,action){
            return {
                ...state,
                sysDiaRowOne:{
               ...action.payload
                }
                    };
                },
                //保存后台查询信息
         sysDiaUserRow(state,action){
            return {
                ...state,
                sysDiaRow:{
               ...action.payload
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


            
            //设置诊断订单添加、修改抽屉页面展示状态
            setDrawerVisibleUpdate(state,action){
            
                return {
                    
                    ...state,
                    drawerVisibleUpdate:action.payload,
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
         //选择多选框
         selectRows(state,action){
            return {
                ...state,
                deleteDisabled:action.payload.deleteDisabled,
                selectedRows:action.payload.selectedRows,
                selectedRowKeys:action.payload.selectedRowKeys,
            };
        },


         //状态信息
         sysDiasStartRow(state,action){
            return {
                ...state,
                sysDiasStartRow:{
               ...action.payload.data
                },
                sysDiasStartRow:false,
             };
        },

        

          //专家信息回复保存信息
          ExpertReplyRow(state,action){
            return {
                ...state,
                expertReplyRow:{
               ...action.payload
                },
                
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