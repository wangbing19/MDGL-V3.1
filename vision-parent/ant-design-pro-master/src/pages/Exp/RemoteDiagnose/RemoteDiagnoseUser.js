import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip,Modal,DatePicker  } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import styles from '@/less/config.less';
import moment from 'moment';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import RemoteDiagnoseUserDrawer from './RemoteDiagnoseUserDrawer.js';
import RemoteDiagnoseUserUpDrawer from './RemoteDiagnoseUserUpDrawer.js';


const FormItem = Form.Item;
const Option = Select.Option;
@connect(({remoteDiagnoseUser, loading }) => ({
    remoteDiagnoseUser,
    loading: loading.models.remoteDiagnoseUser,
  }))
@Form.create()
class RemoteDiagnoseUser extends React.Component {
    constructor(props) {
        super(props);
        this.state = {  
         
      };
    }

           //生命周期函数
    componentDidMount=()=>{
       
     this.getRemoteDiagnose();
     }

     getRemoteDiagnose=(value)=>{
       const { dispatch } = this.props;
       dispatch({
           type:"remoteDiagnoseUser/fetch",
           payload:{
            pageCurrent:1,
            orgId:1,
            pageSize:20,
            registerUser:'admin',
            ...value,
        }
       });
     }


        //修改订单状态
    updateState=(row)=>{
        const { dispatch } =this.props;
        dispatch({
            type:'symptomType/updateState',
            payload:{
                id:row.id,
                registerParentid:row.registerParentid,
                modifiedUser:'admin',//后期加
                valid: row.valid==1?0:1,
            },
            callback:response=>{
                message.info(response.msg);
            }
        })
    }


         //添加修改跳转页面
         showDrawer=(row)=>{
             
            const {  remoteDiagnoseUser:{ drawerVisible },dispatch } =this.props;
           //历史症状记录
            dispatch({
                type:"remoteDiagnoseUser/setDrawerVisible",
                payload:!drawerVisible,
            })
             if(row.id){
                dispatch({
                    type:'remoteDiagnoseUser/getExpSymptomsDescribed',
                     payload:{
                        registerUserId:row.registerUserId,
                        remoteDiagnoseId:row.id,
                     },
                 });
                 //查专家信息回复
                 dispatch({
                    type:'remoteDiagnoseUser/getExpExpertReply',
                     payload:{
                        registerUserId:row.registerUserId,
                        remoteDiagnoseId:row.id,
                     },
                 });

             }
        }

    //订单添加修改跳转页面
        showDrawerUpdate=(row)=>{
            const {  remoteDiagnoseUser:{ drawerVisibleUpdate },dispatch } =this.props;
            dispatch({
                type:"remoteDiagnoseUser/setDrawerVisibleUpdate",
                payload:!drawerVisibleUpdate,
            })


            if(row.id){
                dispatch({
                    type:'remoteDiagnoseUser/getExpRemoteDiagnoseOne',
                    payload:{
                            id:row.id,
                     },
                 });

         

            }
            //查询所有专家名
            dispatch({
                type:'remoteDiagnoseUser/getRemoteDiagnoseNameAll',
                payload:{
                        
                 },
             });

             
        }



        columns=[

    {
        title: '序号',
        dataIndex: 'time',
        render:(text,record,index)=>`${index+1}`, 
       
        width: "5%",
    },
    {
        title: '门店名',
        dataIndex: 'deptName',
        width: "10%",
      
       
    },
    {
        title: '门店地址',
        dataIndex: 'deptSite',
        width: "15%",
      
    },
    {
        title: '客户名',
        dataIndex: 'customerName',
        width: "5%",
        
    },
    {
        title: '客户电话',
        dataIndex: 'customerTel',
        width: "10%",
       
    },
    {
        title: '专家名',
        dataIndex: 'expertName',
        width: "10%",
    },
    {
        title: '次数',
        dataIndex: 'timeNumber',
        width: "5%",
    },
    {
        title: '签名',
        dataIndex: 'sendName',
        width: "5%",
    },
    {
        title: '签名电话',
        dataIndex: 'sendTel',
        width: "10%",
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
        title: '状态',
        dataIndex: 'valid',
        width: "5%",
        render: (text,row) => (
            <div onClick={this.updateState.bind(this,row)} style={{color:text==1?"green":"red"}}>
               {text==1?"已解决":"未解决"}
            </div>
        ),
    },
    {
        title: '详情',
        dataIndex: 'permission',
        width: "10%",

        render: (text,row) => (
            <div>
               <Button  type="primary" icon={'message'} onClick={this.showDrawer.bind(this,row)}/>&nbsp;
               <Button  type="primary" icon={'edit'} onClick={this.showDrawerUpdate.bind(this,row)}/>
            </div>
        ),
        
    },
  
 
]

    render() {

        const { remoteDiagnoseUser: { data, selectedRows, deleteDisabled, msg, selectedRowKeys}, 
        
        form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
        return(
            <div>
               
               
                  <div className={styles.titleSeting}>
                    专家诊断记录列表
                 </div>
                 <br></br>


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
                            <Button type="primary" icon={'plus'} onClick={this.showDrawerUpdate}  title="添加" />&nbsp;
                            <Button type="primary" icon={'delete'}   title="删除" />
                            <div>
                            
                            </div>
                            </div>
               <div >
                     {/* 分页表格 */}
                    <StandardTable
                    
                             selectedRows={selectedRows}
                             loading={loading}
                             data={data}
                            columns={this.columns}
                            type='remoteDiagnoseUser'
                             queryFormData={getFieldsValue()}
                             dispatch={dispatch}
                             deleteDisabled={deleteDisabled}
                             selectedRowKeys={selectedRowKeys}
                    /> 
                <RemoteDiagnoseUserDrawer/>
                <RemoteDiagnoseUserUpDrawer/>
                </div>
            </div>
        )
    }
}
export default RemoteDiagnoseUser;
