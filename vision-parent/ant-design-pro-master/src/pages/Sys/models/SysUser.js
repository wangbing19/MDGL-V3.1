import { getUser,getSysUserById ,updateUser,addSysUser,getRoleName,getRoleNameAll} from '@/services/sysUser';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';
export default {
    namespace: 'sysUser',
    state: {
        //选择框点击后存储数据
        selectedRows:[],
          //查询条件
       queryCriteria:{},
         //删除按钮不可用
       deleteDisabled: true,
       //是否获取角色信息
       cquireRole: true,
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
       cusRow:{
           status:201,
           ok: false,
           msg: "",
           data:{},
       },
       //角色查询行信息
       defaultCheckedList:[],
    
     //角色查询行信息
     plainOptions:[],
       //抽屉页面展示状态
       drawerVisible:false,

   },


 
   effects: {
        //分页查询
        *fetch( { payload }, { select, call, put }) {
        
        
            
            //从cookie获取limit的值，无值从utilsConfig获取
            if(!cookie.load('limit')){
                
                cookie.save('limit',yield select(state => state.sysUser.data.pagination.pageSize));
            }
           
            payload ={
                ...payload,
                pageSize: +cookie.load('limit'),
                
            }
            const formData = formatData(payload);
            const response = yield call(getUser,formData);
            yield put({
                type: 'save',
                payload: {
                    ...response,
                    ...payload,
                },
            });
        },

        
        
          //修改时根据id查询数据
          *getSymptomTypeById( {payload}, { call, put }) {
            
            const formData = formatData(payload);
            const response = yield call(getSysUserById,formData);
            yield put({
                type: 'saveCusRow',
                payload: response,
            });
        },
         //修改时根据id查询数据
         *getRole( {payload}, { call, put }) {
            
        
            const formData = formatData(payload);
            const response = yield call(getRoleName,formData);
            yield put({
                type: 'saveRoleRow',
                payload: response,
            });
        },
        //修改时根据id查询数据
        *getRoleAll( {payload}, { call, put }) {
            
            
            const response = yield call(getRoleNameAll);
            yield put({
                type: 'RoleRowAll',
                payload: response,
            });
        },

        
               //添加
     *add( {payload,callback}, { select, call, put }) {
       
        const response = yield call(addSysUser,payload);
        if(callback) callback(response);
        //刷新页面
        let queryCriteria = yield select(state => state.sysUser.queryCriteria);
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
               
             const response = yield call(updateUser,payload);
             if(callback) callback(response);
             //刷新页面
             let queryCriteria = yield select(state => state.sysUser.queryCriteria);
             const current = yield select(state => state.sysUser.data.pagination.current);
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
    
     //修改时根据id查询数据
     saveCusRow(state,action){
        return {
            ...state,
            cusRow:{
                ...action.payload,
            }
        };
    },
     //给用户添加角色
     saveRoleRow(state,action){
        
        
        return {
            ...state,
            defaultCheckedList:{
                ...action.payload.data,
            },

            

        };
    },
    
     //给用户添加角色
     RoleRowAll(state,action){
        
        
        return {
            ...state,
            plainOptions:{
                ...action.payload.data,
            },

            

        };
    },

    
     //设置抽屉页面展示状态
     setDrawerVisible(state,action){
        
        return {
            
            ...state,
            drawerVisible:action.payload,
           
            
        };
    },

      //设置抽屉页面展示状态
      setCquireRole(state,action){
        
        return {
            
            ...state,
            cquireRole:action.payload,
        };
    },
    }
}