import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import CustomerDrawer from './CustomerDrawer.js';
import moment from 'moment';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({customer, loading }) => ({
    customer,
    loading: loading.models.customer,
}))

//创建表单
@Form.create()

class Customer extends Component {
    constructor(props) {
        super(props);
        this.state = { };
    }

    componentDidMount=()=>{
        this.getConsultation();
    }

    getConsultation=(value)=>{
        const { dispatch } = this.props;
        dispatch({
            type:"customer/fetch",
            payload:{
                pageCurrent:1,
                orgId:1,
                pageSize:20,
                ...value,
            }
        });
    }

    //表单验证，提交查询
    handleSearch = e => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (err) return;
            const { dispatch } = this.props
            dispatch({
                type:'customer/clearQueryCriteria'
            })
            //调用查询方法
            this.getConsultation(fieldsValue);
        });
    };

    //删除
    delete=()=>{
        const { dispatch ,customer:{selectedRowKeys} } = this.props;
        deleteData(selectedRowKeys,'customer',dispatch);//通过id删除数据
    }

    columns=[
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`,
            width: "5%",
        },
        {
            title: '姓名',
            dataIndex: 'name',
            width: "8%",
        },
        {
            title: '联系电话',
            dataIndex: 'tel',
            width: "9%",
        },
        {
            title: '已充值金额/账户余额',
            dataIndex: 'balance',
            width: "9%",
            render: (text,row) => (
                <div>
                   {row.money}/{row.balance}
                </div>
            ),
        },
        {
            title: '训练数',
            dataIndex: 'totalTrainingTime',
            width: "8%",
            render: (text,row) => (
                <div>
                   {row.totalTrainingTime}/{row.timesOfTraining}
                </div>
            ),
        },
        {
            title: '充值记录',
            dataIndex: 'rechargeCount',
            width: "5%",
        },
        {
            title: '课程记录',
            dataIndex: 'scheduleCount',
            width: "5%",
        },
        {
            title: '状态',
            dataIndex: 'state',
            width: "5%",
        },
        {
            title: '咨询/诊断表',
            dataIndex: 'balance',
            width: "8%",
            render:(text,row) =>(
                <div>
                   <span>咨询</span>/<span>诊断表</span>
                </div>
            ),
        },
        {
            title: '主监护人',
            dataIndex: 'guardian',
            width: "10%",
        },
        {
            title: '上次训练时间',
            dataIndex: 'lastTrain',
            width: "15%",
            // render:(text,row) =>(
            //     <div>
            //         {
            //             moment(text).format('YYYY-MM-DD HH:mm:ss')}
            //     </div>
            // ),
        },
        {
            title: '操作',
            // width: "10%",
            render: (text,row) => (
                <div>
                   <Button  type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}/>&nbsp;
                   <Button  type="primary" icon={'plus'} onClick={this.showDrawer.bind(this,row)} title={"添加课程表"}/>
                </div>
            ),
        },
    ]
    
    //添加修改跳转页面
    showDrawer=(row)=>{
        const {  customer:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"customer/setDrawerVisible",
            payload:!drawerVisible,
        })
        if(row.id){
            dispatch({
                type:'customer/getCustomerById',
                payload:{
                    id:row.id,
                    orgId:row.orgId,
                },
            })
        }
    }

    render() {
        const { customer: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
                form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
        
        return (
                
            <div>
                <div className={configStyles.content}>
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
                            type='customer'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                    {/* 修改，添加 */}
                    <CustomerDrawer/>
                </div>
            </div>
        );
    }
}

export default Customer;