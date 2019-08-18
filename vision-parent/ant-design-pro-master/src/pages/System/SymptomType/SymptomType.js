import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip, message } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import SymptomTypeDrawer from './SymptomTypeDrawer.js';
import moment from 'moment';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({symptomType, loading }) => ({
    symptomType,
    loading: loading.models.symptomType,
}))

//创建表单
@Form.create()

class SymptomType extends Component {
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
            type:"symptomType/fetch",
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
                type:'symptomType/clearQueryCriteria'
            })
            //调用查询方法
            this.getConsultation(fieldsValue);
        });
    };

    //删除
    delete=()=>{
        const { dispatch ,symptomType:{selectedRowKeys} } = this.props;
        deleteData(selectedRowKeys,'symptomType',dispatch);//通过id删除数据
    }

    columns=[
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`,
            width: "5%",
        },
        {
            title: '服务名称',
            dataIndex: 'title',
            width: "15%",
        },
        {
            title: '服务描述',
            dataIndex: 'describes',
            // width: "25%",
        },
        {
            title: '状态',
            dataIndex: 'state',
            width: "5%",
            render: (text,row) => (
                <div onClick={this.updateState.bind(this,row)} style={{color:text==1?"green":"red"}}>
                   {text==1?"有效":"失效"}
                </div>
            ),
        },
        {
            title: '创建时间',
            dataIndex: 'gmtCreate',
            width: "15%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        {
            title: '修改时间',
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
            width: "10%",
            render: (text,row) => (
                <div>
                   <Button  type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}/>
                </div>
            ),
        },
    ]
    
    //修改用户状态
    updateState=(row)=>{
        const { dispatch } =this.props;
        dispatch({
            type:'symptomType/updateState',
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

    //添加修改跳转页面
    showDrawer=(row)=>{
        const {  symptomType:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"symptomType/setDrawerVisible",
            payload:!drawerVisible,
        })
        if(row.id){
            dispatch({
                type:'symptomType/getSymptomTypeById',
                payload:{
                    id:row.id,
                    orgId:row.orgId,
                },
            })
        }
    }

    render() {
        const { symptomType: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
                form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
        return (
                
            <div>
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
                            type='symptomType'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                    {/* 修改，添加 */}
                    <SymptomTypeDrawer/>
                </div>
            </div>
        );
    }
}

export default SymptomType;