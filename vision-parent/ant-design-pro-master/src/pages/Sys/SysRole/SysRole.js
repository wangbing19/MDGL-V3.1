import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import SysRoleDrawer from './SysRoleDrawer.js';
import moment from 'moment';
const FormItem = Form.Item;
const Option = Select.Option;
@connect(({sysRole, loading }) => ({
    sysRole,
    loading: loading.models.sysRole,
}))
//创建表单
@Form.create()

class SysRole extends Component{
    constructor(props) {
        super(props);
        this.state = { };
    }

    componentDidMount=()=>{
        this.getSysRole();
    }

    columns=[
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`,
            width: "5%",
        },
        {
            title: '角色名称',
            dataIndex: 'name',
            width: "10%",
        },
        {
            title: '备注',
            dataIndex: 'note',
            width: "25%",
        },
        {
            title: '创建时间',
            dataIndex: 'createdTime',
            width: "15%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
           
        },
        {
            title: '修改时间',
            dataIndex: 'modifiedTime',
            width: "15%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
            
        },
        {
            title: '创建用户',
            dataIndex: 'createdUser',
            width: "10%",
        },
        {
            title: '修改用户',
            dataIndex: 'modifiedUser',
            width: "10%",
        },
        
        {
            title: '操作',
            width: "10%",
            render: (text,row) => (
                <div>
                   <Button  type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}/>
                </div>
            ),
        },
        
       
    ]


     //添加修改跳转页面
     showDrawer=(row)=>{
        const {  sysRole:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"sysRole/setDrawerVisible",
            payload:!drawerVisible,
        })
        if(row.id){
            dispatch({
                type:'sysRole/getConsultationById',
                payload:{
                    id:row.id,
                },
            })
        }
    }

    delete=()=>{
        const { dispatch ,sysRole:{selectedRowKeys} } = this.props;
      
       deleteData(selectedRowKeys,'sysRole',dispatch);//通过id删除数据
    }
    getSysRole=(value)=>{
        const { dispatch } = this.props;
        dispatch({
            type:"sysRole/fetch",
            payload:{
                pageCurrent:1,
                orgId:1,
                pageSize:20,
                ...value,
            }
        });

        
    }


    render() {

        const { sysRole: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, },  form: { getFieldDecorator,getFieldsValue },loading, dispatch} = this.props;
      
        return (
            <div>
                    <div className={configStyles.content}>
                        <div className={configStyles.tableListForm}>
                            <Form onSubmit={this.handleSearch} layout="inline">
                                <Row >
                                    <Col md={8} sm={24}>
                                        <FormItem label="角色名称">
                                            {getFieldDecorator('name')(<Input placeholder="请输入"/>)}
                                        </FormItem>
                                    </Col>
                                
                                    <Col md={8} sm={24} >
                                        <Button type="primary" htmlType="submit">查询</Button>
                                    </Col>
                                </Row>
                            </Form>       
                        </div>
                        <div className={configStyles.rightButton} >
                            <Button type="primary" icon={'plus'} onClick={this.showDrawer} title="添加" />&nbsp;
                            <Button type="primary" icon={'delete'} onClick={this.delete} disabled={deleteDisabled} title="删除" />
                        </div>
                    </div>

                  <div >
                    {/* 分页表格 */}
                    <StandardTable
                            selectedRows={selectedRows}
                            loading={loading}
                            data={data}
                            columns={this.columns}
                            type='sysRole'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                     {/* 修改，添加 */}
                     <SysRoleDrawer/>
                </div>
            </div>
        )
    }
}

export default SysRole;