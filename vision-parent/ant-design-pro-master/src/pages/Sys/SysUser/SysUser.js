import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';

import {deleteData} from '@/utils/dataUtils';
import moment from 'moment';
import SysUserDrawer from './SysUserDrawer.js';

const FormItem = Form.Item;
const Option = Select.Option;
@connect(({sysUser, loading }) => ({
    sysUser,
    loading: loading.models.sysUser,
}))
//创建表单
@Form.create()
class SysUser extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            
        };
    }

    //生命周期函数
    componentDidMount=()=>{
       
        this.getUser();
    }

    getUser=(value)=>{
       
        const { dispatch } = this.props;
        dispatch({
            type:"sysUser/fetch",
            payload:{
                pageCurrent:1,
                orgId:1,
                pageSize:20,
                ...value,
            }
        });
    }


        //添加修改跳转页面
        showDrawer=(row)=>{
            const {  sysUser:{ drawerVisible ,cquireRole},dispatch } =this.props;
           
            dispatch({
                type:"sysUser/setDrawerVisible",
                payload:!drawerVisible,
               
            })
           
            if(row.id){
            
                dispatch({
                    type:'sysUser/getSymptomTypeById',
                    payload:{
                        id:row.id,
                    },
                });
                dispatch({
                    type:'sysUser/getRoleAll',
                    
                    
                });
                dispatch({
                    type:'sysUser/getRole',
                    payload:{
                        id:row.id,
                    },
                    
                });
                
            }
        }
    
    columns=[
        
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`, 
           
            width: "5%",
        },
        {
            title: '门店名',
            dataIndex: 'organizationName',
            width: "5%",
            render: (text,row) => (
            
                <div>
                   {row.sysOrganization.organizationName}
                </div>
            ),
           
        },
        {
            title: '门店地址',
            dataIndex: 'organizationAddress',
            width: "10%",
            render: (text,row) => (
            
                <div>
                   {row.sysOrganization.organizationAddress}
                </div>
            ),
        },
        {
            title: '用户名',
            dataIndex: 'userName',
            width: "5%",
            
        },
        {
            title: '邮件',
            dataIndex: 'email',
            width: "10%",
           
        },
        {
            title: '手机号',
            dataIndex: 'mobile',
            width: "10%",
        },
        {
            title: '用户状态',
            dataIndex: 'userStatus',
            width: "5%",
        },
        {
            title: '顾客上限',
            dataIndex: 'customerLimit',
            width: "5%",
        },
        {
            title: ' 门店上限',
            dataIndex: 'deptLimit',
            width: "5%",
        },
        {
            title: ' 备注',
            dataIndex: 'remark',
            width: "10%",
        },
        {
            title: ' 创建时间',
            dataIndex: 'createTime',
            width: "10%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        {
            title: '修改时间',
            dataIndex: 'modifiedTime',
            width: "10%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        
        {
            title: '操作',
            width: "10%",
            render: (text,row) => (
                <div>
                 
                {/* {<Button type="primary"  size="small" >预约时间</Button> */}
                <Button type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}></Button>
                
                </div>
            ),
        },
    ]


    render() {


        const { sysUser: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
        
        form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
    
        return (
            <div>



                    
                <div>
                <div className={configStyles.tableListForm}>
                            <Form onSubmit={this.handleSearch} layout="inline">
                                <Row >
                                    <Col md={8} sm={24}>
                                        <FormItem label="名称">
                                            {getFieldDecorator('name')(<Input placeholder="请输入"/>)}
                                        </FormItem>
                                    </Col>
                                    <Col md={8} sm={24} >
                                        <FormItem label="电话">
                                            {getFieldDecorator('tel')(<Input placeholder="请输入"/>)}
                                        </FormItem>
                                    </Col>
                                    <Col md={8} sm={24} >
                                        <Button type="primary" htmlType="submit">查询</Button>
                                    </Col>
                                </Row>
                            </Form>       
                        </div>
                <div className={configStyles.rightButton} >
                            <Button type="primary" icon={'edit'}  >预约时间设置</Button>
                            <hr/>
                            <Button type="primary" icon={'plus'} onClick={this.showDrawer}  title="添加" />&nbsp;
    
                            <Button type="primary" icon={'delete'}   title="删除" />
                            <div>
                            
                            </div>
                            
                 </div>

                </div>
                    <div >
                     {/* 分页表格 */}
                    <StandardTable
                    
                            selectedRows={selectedRows}
                            loading={loading}
                            data={data}
                            columns={this.columns}
                            type='sysUser'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    /> 
               
               <SysUserDrawer/>
                </div>
            </div>
        )
    }
}
export default SysUser;