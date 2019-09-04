import {getPreSymptomTypeAll } from '@/services/PreSymptomTypeMenus';
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
        //抽屉页面展示状态
        drawerVisible:false,
        treeList:[],
    },

    effects: {

           //查询所有症状数据用于导航菜单使用
           *getPreSymptomTypeAll( {payload}, { call, put }) {
          
            const formData = formatData(payload);
            const response = yield call(getPreSymptomTypeAll,formData);
            yield put({
                type: 'savePreRow',
                payload: response,
            });
        },

    },
    reducers: {
         
           //修改时根据id查询数据
           savePreRow(state,action){
            return {
                ...state,
                treeList:{
                    ...action.payload.data
                }
            };
        },

        
    }
}