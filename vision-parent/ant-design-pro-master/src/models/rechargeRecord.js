import { getList, deleted, add, //getById, update,
        } from '@/services/rechargeRecord';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';

export default {
    namespace: 'rechargeRecord',

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
        //选择框点击后存储id值
        selectedRowKeys:[],
        //抽屉页面展示状态
        drawerVisible:false,
        customer:{},
    },

    effects: {
        //分页查询
        *fetch( { payload }, { select, call, put }) {
            //从cookie获取limit的值，无值从utilsConfig获取
            if(!cookie.load('limit')){
                cookie.save('limit',yield select(state => state.rechargeRecord.data.pagination.pageSize));
            }
            payload ={
                ...payload,
                pageSize: +cookie.load('limit'),
                name: payload.name?payload.name:'',
            }
            const formData = formatData(payload);
            const response = yield call(getList,formData);
            yield put({
                type: 'save',
                payload: {
                    ...response,
                    ...payload,
                },
            });
        },
        //添加
        *add( {payload,callback}, { select, call, put }) {
            const response = yield call(add,payload);
            if(callback) callback(response);
            //刷新页面
            let queryCriteria = yield select(state => state.rechargeRecord.queryCriteria);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: 1,
                    orgId: 1,
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
            debugger;
            return {
                ...state,
                drawerVisible:action.payload,
            };
        },
        setCustomer(state,action){
            return {
                ...state,
                customer:action.payload,
            };
        },
    },

};