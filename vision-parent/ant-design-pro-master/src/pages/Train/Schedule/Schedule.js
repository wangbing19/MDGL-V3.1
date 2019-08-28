import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip, message } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import ScheduleDrawer from './ScheduleDrawer.js';
import moment from 'moment';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({schedule, loading }) => ({
    schedule,
    loading: loading.models.schedule,
}))

//创建表单
@Form.create()

class Schedule extends Component {
    constructor(props) {
        super(props);
        this.state = { };
    }

    componentDidMount=()=>{
        if(this.props.location.params){
            this.getConsultation(this.props.location.params);
        } else{
            this.getConsultation();
        }
    }

    getConsultation=(value)=>{
        const { dispatch } = this.props;
        dispatch({
            type:"schedule/fetch",
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
                type:'schedule/clearQueryCriteria'
            })
            //调用查询方法
            this.getConsultation(fieldsValue);
        });
    };

    //删除
    delete=()=>{
        const { dispatch ,schedule:{selectedRowKeys} } = this.props;
        deleteData(selectedRowKeys,'schedule',dispatch);//通过id删除数据
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
            width: "10%",
        },
        {
            title: '课程名称',
            dataIndex: 'courseTitle',
            width: "10%",
        },
        {
            title: '课程价格',
            dataIndex: 'priceOfCourse',
            width: "10%",
        },
        {
            title: '课程建议训练数',
            dataIndex: 'courseTraining',
            width: "10%",
        },
        {
            title: '课程项目数',
            dataIndex: 'numberOfCourse',
            width: "8%",
        },
        {
            title: '总价格',
            dataIndex: 'totalPrice',
            width: "8%",
        },
        {
            title: '诊断师',
            dataIndex: 'diagnostics',
            width: "10%",
        },
        {
            title: '时间',
            dataIndex: 'gmtModified',
            width: "15%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD')}
                </div>
            ),
        },
        {
            title: '操作',
            render: (text,row) => (
                <div>
                   <Button  type="primary" icon={'edit'} title="修改" onClick={this.showDrawer.bind(this,row)}/>&nbsp;
                   <Button  type="primary" icon={'edit'} title="添加训练记录" onClick={this.showDrawer.bind(this,row)}/>
                </div>
            ),
        },
    ]
    
    //添加修改跳转页面
    showDrawer=(row)=>{
        const {  schedule:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"schedule/setDrawerVisible",
            payload:!drawerVisible,
        })
        dispatch({
            type:'schedule/getSymptomTypesList',
            payload:{
                pageCurrent:1,
                orgId:1,
                pageSize:100,
            },
        })
        if(row.id){
            dispatch({
                type:'schedule/getScheduleById',
                payload:{
                    id:row.id,
                    orgId:row.orgId,
                },
            })
        }
    }

    render() {
        const { schedule: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
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
                            type='schedule'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                    {/* 修改，添加 */}
                    <ScheduleDrawer/>
                </div>
            </div>
        );
    }
}

export default Schedule;