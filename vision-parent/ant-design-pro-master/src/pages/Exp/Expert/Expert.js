import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip,Modal,DatePicker  } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import ExpertDrawer from './ExpertDrawer.js';
                                   
const FormItem = Form.Item;
const Option = Select.Option;
@connect(({expert, loading }) => ({
    expert,
    loading: loading.models.expert,
}))
//创建表单
@Form.create()
class Expert extends Component {
    constructor(props) {
        super(props);

        this.state = {
         };
    }




    columns=[
        
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`, 
           
            width: "5%",
        },
        {
            title: '专家姓名',
            dataIndex: 'expertName',
            width: "10%",
           
        },
        {
            title: '专家电话',
            dataIndex: 'expertTel',
            width: "20%",
        },
        {
            title: '专家信息',
            dataIndex: 'expertMessage',
            width: "10%",
            
        },
        {
            title: '预约时间',
            dataIndex: 'appointmentTime',
            width: "15%",
           
        },
        {
            title: '创建时间',
            dataIndex: 'gmtCreate',
            width: "10%",
        },
        {
            title: '修改时间',
            dataIndex: 'gmtModified',
            width: "10%",
        },
        {
            title: ' 状态',
            dataIndex: 'delTag',
            width: "10%",
        },
        
        
        {
            title: '操作',
            width: "10%",
            render: (text,row) => (
                <div>
                 
                {/* {<Button type="primary"  size="small" >预约时间</Button> */}
                {/* <Button type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}></Button> */}
                <Button type="primary"  icon={'edit'} onClick={this.showDrawer.bind(this,row)}  >
                         修改
                </Button>
                
                </div>
            ),
        },
    ]

    //生命周期函数
    componentDidMount=()=>{
     
        this.getExpert();
    }

    getExpert=(value)=>{
        
        const { dispatch } = this.props;
        dispatch({
            type:"expert/fetch",
            payload:{
                pageCurrent:1,
                pageSize:20,
                ...value,
            }
        });
    }

     //添加修改跳转页面
     showDrawer=(row)=>{
        const {  expert:{ drawerVisible },dispatch } =this.props;
    
        dispatch({
            type:"expert/setDrawerVisible",
            payload:!drawerVisible,
        })
      
        if(row.id){
            
            dispatch({
                type:'expert/getExpertById',
                payload:{
                    id:row.id,
                },
            })
        }
    }

    delete=()=>{
        const { dispatch ,expert:{selectedRowKeys} } = this.props;
  
       deleteData(selectedRowKeys,'expert',dispatch);//通过id删除数据
    }
    render() {

        const { expert: { data, selectedRows, deleteDisabled, msg, selectedRowKeys}, 
        
        form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
        return(
            <div>
                

                <div>
                <div className={configStyles.tableListForm}>
                            <Form onSubmit={this.handleSearch} layout="inline">
                                <Row >
                                    <Col md={8} sm={24}>
                                        <FormItem label="专家名称">
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
                            <div>
                            
                            </div>
                            
                 </div>

                </div>


                <div >
                     {/* 分页表格 */}
                    <StandardTable
                    
                            selectedRows={selectedRows}
                            loading={loading}
                            data={data}
                            columns={this.columns}
                            type='expert'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    /> 
               
               <ExpertDrawer/> 
                </div>
            </div>
        )
    }
}
export default Expert;