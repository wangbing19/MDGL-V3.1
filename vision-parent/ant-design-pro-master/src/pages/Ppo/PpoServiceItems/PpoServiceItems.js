import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip, message } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import moment from 'moment';
import PpoServiceItemsDrawer from './PpoServiceItemsDrawer.js';
const FormItem = Form.Item;
const Option = Select.Option;



@connect(({ppoServiceItems, loading }) => ({
    ppoServiceItems,
    loading: loading.models.ppoServiceItems,
}))
//创建表单
@Form.create()
class PpoServiceItems extends Component {
    constructor(props) {
        super(props);
        this.state = { };
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
            dataIndex: 'serviceName',
            width: "15%",
        },
        {
            title: '服务描述',
            dataIndex: 'serviceContent',
            width: "30%",
        },
        {
            title: '状态',
            dataIndex: 'serviceState',
            width: "5%",
            render: (text,row) => (
                <div style={{color:text==1?"green":"red"}}>
                   {text==1?"有效":"失效"}
                </div>
            ),
        },
        {
            title: '备注',
            dataIndex: 'serviceRemark',
             width: "15%",
        },
        {
            title: '创建时间',
            dataIndex: 'gmtCreate',
            width: "10%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        {
            title: '修改时间',
            dataIndex: 'gmtModified',
            width: "10%",
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
                   <Button  type="primary" icon={'edit'}  onClick={this.showDrawer.bind(this,row)}/>
                </div>
            ),
        },
    ];

    componentDidMount=()=>{
        this.getPpoServiceItems();
    };

    getPpoServiceItems=(value)=>{
        const { dispatch } = this.props;
        dispatch({
            type:"ppoServiceItems/fetch",
            payload:{
                pageCurrent:1,
                organizationId:1,
                pageSize:20,
                ...value,
            }
        });
    };


      //添加修改跳转页面
      showDrawer=(row)=>{
        const {  ppoServiceItems:{ drawerVisible },dispatch } =this.props;
        
        
        dispatch({
            type:"ppoServiceItems/setDrawerVisible",
            payload:!drawerVisible,
        })
         if(row.id){
             dispatch({
                 type:'ppoServiceItems/getPpoServiceItemsById',
                 payload:{
                    id:row.id,
                   
                 },
             })
         }
    }



    
    render() {

        const { ppoServiceItems: { data, selectedRows, deleteDisabled, msg, selectedRowKeys, }, 
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
                            type='ppoServiceItems'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    />
                    {/* 修改，添加 */}
                   <PpoServiceItemsDrawer/>
                </div>
            </div>
        )
    }


}

export default PpoServiceItems;