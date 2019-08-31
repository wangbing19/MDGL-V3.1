import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import ConsultationDrawer from './ConsultationDrawer.js';
import CustomerDrawer from '@/pages/Cus/Customer/CustomerDrawer'
import moment from 'moment';
import { routerRedux } from 'dva/router';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({consultation, customer, loading }) => ({
    consultation,
    customer,
    loading: loading.models.consultation,
}))

//创建表单
@Form.create()

class Consultation extends Component {
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
            type:"consultation/fetch",
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
                type:'consultation/clearQueryCriteria'
            })
            //调用查询方法
            this.getConsultation(fieldsValue);
        });
    };

    //删除
    delete=()=>{
        const { dispatch ,consultation:{selectedRowKeys} } = this.props;
        deleteData(selectedRowKeys,'consultation',dispatch);//通过id删除数据
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
            width: "15%",
        },
        {
            title: '联系电话',
            dataIndex: 'tel',
            width: "15%",
        },
        {
            title: '裸眼远视力',
            dataIndex: 'ld',
            width: "10%",
            render: (text,row) => (
                <div>
                   {row.ld}/{row.rd}
                </div>
            ),
        },
        {
            title: '矫正远视力',
            dataIndex: 'lcva',
            width: "10%",
            render: (text,row) => (
                <div>
                   {row.lcva}/{row.rcva}
                </div>
            ),
        },
        {
            title: '咨询导师',
            dataIndex: 'tutor',
            width: "10%",
        },
        {
            title: '创建时间',
            dataIndex: 'gmtModified',
            width: "15%",
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
                   <Button  type="primary" icon={'edit'} title="修改" onClick={this.showDrawer.bind(this,row)}/>&nbsp;
                   <Button  type="primary" icon={'plus'} title="添加用户" onClick={this.addCustomer.bind(this,row)}/>
                </div>
            ),
        },
    ]
    
    //添加修改跳转页面
    showDrawer=(row)=>{
        const {  consultation:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"consultation/setDrawerVisible",
            payload:!drawerVisible,
        })
        if(row.id){
            dispatch({
                type:'consultation/getConsultationById',
                payload:{
                    id:row.id,
                    orgId:row.orgId,
                },
            })
        }
    }

    //添加用户
    addCustomer=(row)=>{
        const {  consultation:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"customer/getCustomerByConsultationId",
            payload:{
                consultationId: row.id,
            },
            callback:response=>{
                if(response.data==0){
                    dispatch({
                        type:"customer/setConsultation",
                        payload:row,
                    })
                    dispatch({
                        type:"customer/setDrawerVisible",
                        payload:true,
                    })
                } else if(response.data==1){
                    this.props.dispatch(routerRedux.push({ 
                        pathname: '/cus/customer/customer',
                        params:{
                            name:row.name
                        }
                    }))
                }
            }
        })
        
    }

    render() {
        const { consultation: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
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
                            type='consultation'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                    {/* 修改，添加 */}
                    <ConsultationDrawer/>
                    <CustomerDrawer/>
                </div>
            </div>
        );
    }
}

export default Consultation;