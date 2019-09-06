import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import moment from 'moment';
const FormItem = Form.Item;
const Option = Select.Option;
@connect(({sysLog, loading }) => ({
    sysLog,
    loading: loading.models.sysLog,
   
}))

//创建表单
@Form.create()

class SysLog extends Component{
    constructor(props) {
        super(props);
        this.state = { };
    }
    componentDidMount=()=>{
     
        this.getSysLog();
    }
    getSysLog=(value)=>{
        
        const { dispatch } = this.props;
        dispatch({
            type:"sysLog/fetch",
            payload:{
                pageCurrent:1,
                orgId:1,
                pageSize:20,
                ...value,
            }
        });
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
            width: "10%",
        },
        {
            title: '门店地址',
            dataIndex: 'organizationAddress',
            width: "10%",
        },
        {
            title: '账户名',
            dataIndex: 'username',
            width: "5%",
           
        },
        {
            title: '用户操作',
            dataIndex: 'operation',
            width: "15%",
            
        },
        {
            title: '请求方法',
            dataIndex: 'method',
            width: "15%",
        },
        {
            title: '请求参数',
            dataIndex: 'params',
            width: "10%",
        },
        {
            title: '执行时长(毫秒)',
            dataIndex: 'time',
            width: "10%",
        },
        {
            title: 'IP地址',
            dataIndex: 'ip',
            width: "10%",
        },
        {
            title: '创建时间',
            dataIndex: 'createdTime',
            width: "10%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
       
    ]

    render() {
        const { sysLog: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, form: { getFieldDecorator,getFieldsValue }  ,loading, dispatch} = this.props;
               
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
                            <Button type="primary" icon={'plus'} onClick={this.showDrawer} title="添加" />&nbsp;
    
                            <Button type="primary" icon={'delete'} onClick={this.delete} disabled={deleteDisabled} title="删除" />
                            <div>
                            
                            </div>
                            
                 </div>

                </div>
                   
                <div>
                <StandardTable
                            selectedRows={selectedRows}
                            loading={loading}
                             data={data}
                             columns={this.columns}
                            type='sysLog'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                </div>
               
                   
            </div>
        )
    }
}
export default SysLog;
