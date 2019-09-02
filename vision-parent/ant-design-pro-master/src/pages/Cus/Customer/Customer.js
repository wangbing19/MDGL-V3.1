import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip, message } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import CustomerDrawer from './CustomerDrawer.js';
import moment from 'moment';
import { routerRedux } from 'dva/router';
import ConsultationDrawer from '@/pages/Cus/Consultation/ConsultationDrawer';
import DiagnoseDrawer from '@/pages/Cus/Diagnose/DiagnoseDrawer';
import ScheduleDrawer from '@/pages/Train/Schedule/ScheduleDrawer';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({customer, consultation, diagnose, schedule, loading }) => ({
    customer,
    consultation,
    diagnose,
    schedule,
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
        this.getConsultation(this.props.location.params);
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
                <div onClick={this.showTotalTrainingTime.bind(this,row)} style={{color:'blue'}}>
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
            render: (text,row) => (
                <div onClick={this.showScheduleCount.bind(this,row)} style={{color:'blue'}}>
                    {text}
                </div>
            ),
        },
        {
            title: '状态',
            dataIndex: 'state',
            width: "5%",
            render: (text,row) => (
                <div onClick={this.updateState.bind(this,row)} style={{color:text==1?"green":"red"}}>
                    {text==1?"正常":"禁用"}
                </div>
            ),
        },
        {
            title: '咨询/诊断表',
            dataIndex: 'balance',
            width: "8%",
            render:(text,row) =>(
                <div>
                   <span onClick={this.showConsultation.bind(this,row)} className={configStyles.onClickTitle} >咨询</span>
                   /<span onClick={this.showDiagnose.bind(this,row)} className={configStyles.onClickTitle} >诊断表</span>
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
            render:(text,row) =>(
                <div>
                    {text?moment(text).format('YYYY-MM-DD HH:mm:ss'):''}
                </div>
            ),
        },
        {
            title: '操作',
            render: (text,row) => (
                <div>
                   <Button  type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)} title={"修改用户"}/>&nbsp;
                   <Button  type="primary" icon={'plus'} onClick={this.addSchedule.bind(this,row)} title={"添加课程表"}/>
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

    //跳转训练数页面
    showTotalTrainingTime=(row)=>{
        this.props.dispatch(routerRedux.push({ 
            pathname: '/train/traInformationrecord/traInformationrecord',
            params: {
                customerId:row.id
            }
        }))
    }

    //跳转课程记录
    showScheduleCount=(row)=>{
        this.props.dispatch(routerRedux.push({ 
            pathname: '/train/schedule/schedule',
            params: {
                customerId:row.id
            }
        }))
    }

    //修改用户状态
    updateState=(row)=>{
        const { dispatch } =this.props;
        dispatch({
            type:'customer/updateCustomerState',
            payload:{
                id:row.id,
                orgId:row.orgId,
                state: row.state==1?0:1,
            },
            callback:response=>{
                message.info(response.msg);
            }
        })
    }

    //跳转咨询表
    showConsultation=(row)=>{
        const {  consultation:{ drawerVisible },dispatch } =this.props;
        
        if(row.consultationId){
            dispatch({
                type:"consultation/setDrawerVisible",
                payload:!drawerVisible,
            })
            dispatch({
                type:'consultation/getConsultationById',
                payload:{
                    id:row.consultationId,
                    orgId:row.orgId,
                },
            })
        } else{
            message.info("数据可能已经不存在，请刷新重试");
        }
    }

    //跳转诊断表
    showDiagnose=(row)=>{
        const {  diagnose:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"diagnose/setDrawerVisible",
            payload:!drawerVisible,
        })
        dispatch({
            type:'diagnose/getDiagnoseById',
            payload:{
                id:row.diagnoseId,
                orgId:row.orgId,
            },
            callback:response=>{
                if(!response.data){
                    dispatch({
                        type:'diagnose/setCustomer',
                        payload:row,
                    })
                }
                if(response.data){
                    dispatch({
                        type:'diagnose/saveDiaRow',
                        payload:response,
                    })
                }
            }
        })
        
    }

    //跳转到添加课程表页面
    addSchedule=(row)=>{
        const {  schedule:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:'schedule/getSymptomTypesList',
            payload:{
                pageCurrent:1,
                orgId:1,
                pageSize:100,
            },
            callback:response=>{
                if(response.ok){
                    if(response.data.rowCount!=0){
                        dispatch({
                            type:"schedule/setDrawerVisible",
                            payload:!drawerVisible,
                        })
                        dispatch({
                            type:"schedule/setCustomer",
                            payload:row,
                        })
                    } else if(response.data.rowCount==0){
                        message.info("请添加资源信息");
                        this.props.dispatch(routerRedux.push({ 
                            pathname: '/system/symptomType/symptomType',
                        }))
                    }
                }
            }
        })
    }

    render() {
        const { customer: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
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
                        {/* <Button type="primary" icon={'plus'} onClick={this.showDrawer} title="添加" />&nbsp; */}
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
                    <ConsultationDrawer/>
                    <DiagnoseDrawer/>
                    <ScheduleDrawer/>
                </div>
            </div>
        );
    }
}

export default Customer;