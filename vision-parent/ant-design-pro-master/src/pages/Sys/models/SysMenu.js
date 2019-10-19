import { getSysMenuAll,findMenusOne,doFindMenuList,updateMenu,addSysMenu,deleteMenus} from '@/services/sysMenu';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';
export default {
    namespace: 'sysMenu',
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
       sysMenuRow:{
           status:201,
           ok: false,
           msg: "",
           data:[],
       },
       treeData:[],
            menusId:"",
         //抽屉页面展示状态
         drawerVisible:false,
   },


   
   effects: {


               //分页查询
        *fetch( { payload }, { select, call, put }) {
         
            //从cookie获取limit的值，无值从utilsConfig获取
            if(!cookie.load('limit')){
                cookie.save('limit',yield select(state => state.sysMenu.data.pagination.pageSize));
            }
            payload ={
                ...payload,
                pageSize: +cookie.load('limit'),
                title: payload.title?payload.title:'',
            }
            const formData = formatData(payload);
            const response = yield call(doFindMenuList,formData);
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
            
            const response = yield call(updateMenu,payload);
            if(callback) callback(response);
            //刷新页面
            let queryCriteria = yield select(state => state.sysMenu.queryCriteria);
            const current = yield select(state => state.sysMenu.data.pagination.current);
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
                const response = yield call(addSysMenu,payload);
                if(callback) callback(response);
                //刷新页面
                let queryCriteria = yield select(state => state.sysMenu.queryCriteria);
                yield put({
                    type: 'fetch',
                    payload: {
                        ...queryCriteria,
                        pageCurrent: 1,
                      
                    },
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
    *findMenusOne( {payload}, { call, put }) {
        
        const formData = formatData(payload);
        const response = yield call(findMenusOne,formData);
        yield put({
            type: 'sysMenuRowOne',
            payload: response,
        });
    },

    //删除
    *remove({payload,callback}, { select, call, put }) {
        const response = yield call(deleteMenus,payload);
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
        let queryCriteria = yield select(state => state.sysMenu.queryCriteria);
        yield put({
            type: 'fetch',
            payload: {
                ...queryCriteria,
                pageCurrent: 1,
              
            },
        });
    },

},


reducers: {


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
            sysMenuRow:{
                status:201,
                ok: false,
                msg: "",
                data:{},
            },
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
    
    //保存后台查询到的菜单信息
    sysMenuRowOne(state,action){
        
       
        return {
            ...state,
            sysMenuRow:{
           ...action.payload
       }
            
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
        //修改父级id
        setMenusId(state,action){
           
            return {
                ...state,
                menusId:action.payload,
            };
        },
        
        

    
    }
}