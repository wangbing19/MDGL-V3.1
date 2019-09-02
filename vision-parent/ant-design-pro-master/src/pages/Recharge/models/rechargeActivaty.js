import { getList, deleted, add, getById, update,
        } from '@/services/rechargeActivaty';
import {formatData, FormdateFormat} from '@/utils/dataUtils';
import cookie from 'react-cookies';

export default {
    namespace: 'rechargeActivaty',

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
        activatyRow:{
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
                cookie.save('limit',yield select(state => state.rechargeActivaty.data.pagination.pageSize));
            }
            payload ={
                ...payload,
                pageSize: +cookie.load('limit'),
                title: payload.title?payload.title:'',
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
        //删除
        *remove({payload,callback}, { select, call, put }) {
            const response = yield call(deleted,payload);
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
            let queryCriteria = yield select(state => state.rechargeActivaty.queryCriteria);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: 1,
                    orgId: 1,
                },
            });
        },
        //添加
        *add( {payload,callback}, { select, call, put }) {
            const response = yield call(add,payload);
            if(callback) callback(response);
            //刷新页面
            let queryCriteria = yield select(state => state.rechargeActivaty.queryCriteria);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: 1,
                    orgId: 1,
                },
            });
        },
        //修改
        *update( {payload,callback}, { select,call, put }) {
            const response = yield call(update,payload);
            if(callback) callback(response);
            //刷新页面
            let queryCriteria = yield select(state => state.rechargeActivaty.queryCriteria);
            const current = yield select(state => state.rechargeActivaty.data.pagination.current);
            yield put({
                type: 'fetch',
                payload: {
                    ...queryCriteria,
                    pageCurrent: current,
                    orgId: 1,
                },
            });
        },
        //修改时根据id查询数据
        *getById( {payload}, { call, put }) {
            const formData = formatData(payload);
            const response = yield call(getById,formData);
            yield put({
                type: 'saveActivatyRow',
                payload: response,
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
        saveActivatyRow(state,action){
            return {
                ...state,
                activatyRow:{
                    ...action.payload,
                }
            };
        },
        //设置按id查询数据清空
        clearFeomData(state){
            return {
                ...state,
                activatyRow:{
                    status:201,
                    ok: false,
                    msg: "",
                    data:{},
                },
            };
        },
    },

};