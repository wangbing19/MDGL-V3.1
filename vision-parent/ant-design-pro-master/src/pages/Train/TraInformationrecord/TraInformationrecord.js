import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip, message } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import TraInformationrecordDrawer from './TraInformationrecordDrawer.js';
import moment from 'moment';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({traInformationrecord, loading }) => ({
    traInformationrecord,
    loading: loading.models.traInformationrecord,
}))

//创建表单
@Form.create()

class TraInformationrecord extends Component {
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
            type:"traInformationrecord/fetch",
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
                type:'traInformationrecord/clearQueryCriteria'
            })
            //调用查询方法
            this.getConsultation(fieldsValue);
        });
    };

    //删除
    delete=()=>{
        const { dispatch ,traInformationrecord:{selectedRowKeys} } = this.props;
        deleteData(selectedRowKeys,'traInformationrecord',dispatch);//通过id删除数据
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
            title: '裸眼视力',
            dataIndex: 'rvision',
            width: "9%",
            render: (text,row) => (
                <div>
                   {row.rvision}/{row.lvision}
                </div>
            ),
        },
        {
            title: '评分',
            dataIndex: 'grade',
            width: "5%",
        },
        {
            title: '评价',
            dataIndex: 'evaluate',
            width: "15%",
        },
        {
            title: '项目内容',
            dataIndex: 'content',
            width: "15%",
        },
        {
            title: '导师',
            dataIndex: 'tutor',
            width: "10%",
        },
        {
            title: '结束时间',
            dataIndex: 'endTime',
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
                   <Button  type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}/>
                </div>
            ),
        },
    ]
    
    //添加修改跳转页面
    showDrawer=(row)=>{
        const {  traInformationrecord:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"traInformationrecord/setDrawerVisible",
            payload:!drawerVisible,
        })
        if(row.id){
            dispatch({
                type:'traInformationrecord/getTraInforById',
                payload:{
                    id:row.id,
                    orgId:row.orgId,
                },
            })
        }
    }

    render() {
        const { traInformationrecord: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
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
                            type='traInformationrecord'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                    {/* 修改，添加 */}
                    <TraInformationrecordDrawer/>
                </div>
            </div>
        );
    }
}

export default TraInformationrecord;