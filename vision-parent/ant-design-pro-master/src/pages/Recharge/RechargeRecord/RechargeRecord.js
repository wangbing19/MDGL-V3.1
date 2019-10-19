import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip, message } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import RechargeRecordDrawer from './RechargeRecordDrawer.js';
import moment from 'moment';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({rechargeRecord, loading }) => ({
    rechargeRecord,
    loading: loading.models.rechargeRecord,
}))

//创建表单
@Form.create()

class RechargeRecord extends Component {
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
            type:"rechargeRecord/fetch",
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
                type:'rechargeRecord/clearQueryCriteria'
            })
            debugger;
            //调用查询方法
            this.getConsultation(fieldsValue);
        });
    };



    columns=[
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`,
            width: "5%",
        },
        {
            title: '客户姓名',
            dataIndex: 'name',
            width: "10%",
        },
        {
            title: '客户账户金额',
            dataIndex: 'money',
            width: "10%",
        },
        {
            title: '选择的充值类型',
            dataIndex: 'rechargeType',
        },
        {
            title: '充值金额',
            dataIndex: 'rechargeAmount',
            width: "10%",
        },
        {
            title: '赠送金额',
            dataIndex: 'presentedAmount',
            width: "10%",
        },
        {
            title: '上次充值时间',
            dataIndex: 'lastPayTime',
            width: "12%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            width: "12%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD')}
                </div>
            ),
        },
    ]
    

    //添加修改跳转页面
    showDrawer=()=>{
        const {  rechargeRecord:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"rechargeRecord/setDrawerVisible",
            payload:!drawerVisible,
        })
    }

    render() {
        const { rechargeRecord: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
                form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
        return (
            <div className={configStyles.rightSidePage}>
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
                                    <Button type="primary" htmlType="submit">查询</Button>
                                </Col>
                            </Row>
                        </Form>       
                    </div>
                    <div className={configStyles.rightButton} >
                        <Button type="primary" icon={'plus'} onClick={this.showDrawer} title="添加" />
                    </div>
                </div>
                <div >
                    {/* 分页表格 */}
                    <StandardTable
                            selectedRows={selectedRows}
                            loading={loading}
                            data={data}
                            columns={this.columns}
                            type='rechargeRecord'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                    {/* 修改，添加 */}
                    <RechargeRecordDrawer/>
                </div>
            </div>
        );
    }
}

export default RechargeRecord;