import { getSysRole,updateRole,getRoleById,addRole,deleteSysRole} from '@/services/sysRole';
import { getSysMenuAll} from '@/services/sysMenu';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';
export default {
    namespace: 'sysRole',

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
        cusRow:{
            status:201,
            ok: false,
            msg: "",
            data:{},
        },
        //存菜单信息
        treeData:[],
        treeVisible:true,
        //抽屉页面展示状态
        drawerVisible:false,
    },

    effects: {
          //分页查询
          *fetch( { payload }, { select, call, put }) {
            //从cookie获取limit的值，无值从utilsConfig获取
            if(!cookie.load('limit')){
                cookie.save('limit',yield select(state => state.sysRole.data.pagination.pageSize));
            }
            payload ={
                ...payload,
                pageSize: +cookie.load('limit'),
                name: payload.name?payload.name:'',
                tel: payload.tel?payload.tel:'',
            }
            const formData = formatData(payload);
            const response = yield call(getSysRole,formData);
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
           
            const response = yield call(updateRole,payload);
            if(callback) callback(response);
            //刷新页面
            let queryCriteria = yield select(state => state.sysRole.queryCriteria);
            const current = yield select(state => state.sysRole.data.pagination.current);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: current,
                    orgId: 1,
                },
            });
        },
        
        //添加
        *add( {payload,callback}, { select, call, put }) {
            const response = yield call(addRole,payload);
            if(callback) callback(response);
            //刷新页面
            let queryCriteria = yield select(state => state.sysRole.queryCriteria);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: 1,
                    organizationId: 1,
                },
            });
        },
         //删除
         *remove({payload,callback}, { select, call, put }) {
              
            const response = yield call(deleteSysRole,payload);
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
            let queryCriteria = yield select(state => state.sysRole.queryCriteria);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: 1,
                    organizationId: 1,
                },
            });
        },


        //修改时根据id查询数据
        *getConsultationById( {payload}, { call, put }) {
            const formData = formatData(payload);
            const response = yield call(getRoleById,formData);
           
            yield put({
                type: 'saveSysRoleRow',
                payload: response,
              
                
            });
           
            
        },

         //获取菜单信息
         *getMenus( {payload}, { call, put }) {
        
            const formData = formatData(payload);
            const response = yield call(getSysMenuAll,formData);
            yield put({
                type: 'sysMenuRow',
                payload: response,
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
      //设置抽屉页面展示状态
      setDrawerVisible(state,action){
            
        return {
            ...state,
            drawerVisible:action.payload,
        };
    },
     //修改时根据id查询数据
     saveSysRoleRow(state,action){
       
        return {
            ...state,
            cusRow:{
                ...action.payload,
            }
        };
        
    },

         //保存后台查询到的菜单信息
         sysMenuRow(state,action){
            return {
                ...state,
                treeData:{
               ...action.payload.data
           }
            };
        },

 //设置抽屉页面展示状态
    setTreeVisible(state,action){
            
    return {
        ...state,
        treeVisible:action.payload,
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
}

}