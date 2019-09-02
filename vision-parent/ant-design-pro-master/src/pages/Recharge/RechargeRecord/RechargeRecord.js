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
            //调用查询方法
            this.getConsultation(fieldsValue);
        });
    };

    //删除
    delete=()=>{
        const { dispatch ,rechargeRecord:{selectedRowKeys} } = this.props;
        deleteData(selectedRowKeys,'rechargeRecord',dispatch);//通过id删除数据
    }

    columns=[
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`,
            width: "5%",
        },
        {
            title: '充值活动名称',
            dataIndex: 'title',
            width: "15%",
        },
        {
            title: '充值活动描述',
            dataIndex: 'describe',
            width: "30%",
        },
        {
            title: '状态',
            dataIndex: 'activityState',
            width: "10%",
            render: (text,row) => (
                <div style={{color:text==1?"green":"red"}}>
                   {text==1?"活动开始":(text==0?"活动结束":"活动未开始")}
                </div>
            ),
        },
        {
            title: '活动开始时间',
            dataIndex: 'activityStartTime',
            width: "12%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        {
            title: '活动结束时间',
            dataIndex: 'activityEndTime',
            width: "12%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        {
            title: '操作',
            render: (text,row) => (
                <div>
                   <Button  type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}/>
                </div>
            ),
        },
    ]
    

    //添加修改跳转页面
    showDrawer=(row)=>{
        const {  rechargeRecord:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"rechargeRecord/setDrawerVisible",
            payload:!drawerVisible,
        })
        if(row.id){
            dispatch({
                type:'rechargeRecord/getById',
                payload:{
                    id:row.id,
                    orgId:row.orgId,
                },
            })
        }
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
                                        {getFieldDecorator('title')(<Input placeholder="请输入"/>)}
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