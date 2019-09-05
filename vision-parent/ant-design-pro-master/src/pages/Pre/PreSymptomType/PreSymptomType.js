import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip, message,Menu, Icon,Layout,Modal } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import moment from 'moment';
import BaseDicTree from '@/components/BaseTree/BaseDicTree';
import styles from '@/less/config.less';
import PreSymptomTypeDrawer from './PreSymptomTypeDrawer.js';
const FormItem = Form.Item;
const Option = Select.Option;
const { SubMenu } = Menu;
const { TextArea } = Input;
const {  Footer, Sider, Content } = Layout;
//创建表单
//创建表单
@Form.create()

@connect(({preSymptomType, loading }) => ({
    preSymptomType,
    loading: loading.models.preSymptomType,
}))
class PreSymptomType extends Component {
    constructor(props) {
        super(props);
        this.state = {  visible: false };
    }

    componentDidMount=()=>{
        this.getPreSymptomTypeAll();
    }

    getPreSymptomTypeAll=(value)=>{
        const { dispatch } = this.props;
        dispatch({
            type:"preSymptomType/getPreSymptomTypeAll",
            payload:{
              
                ...value,
            }
        });

        dispatch({
            type:"preSymptomType/fetch",
            payload:{
                pageCurrent:1,
                pageSize:20,
                ...value,
            }
        })


    }
    onSelect=(e)=>{

        this.showModal(e);
       
    
    }

    //对话按钮状态设置并查询处方信息
    showModal = (value) => {
        
        const { dispatch } = this.props;
    
        
        dispatch({
            type:"preSymptomType/findSymptomDesc",
            payload:{
                id:value,
                
            }
        });

        this.setState({
            visible: true,
          });

      };
    
      handleOk = e => {
        console.log(e);
        this.setState({
          visible: false,
        });
      };

      handleCancel = e => {
          
        console.log(e);
        this.setState({
          visible: false,
        });
      };


      
    //添加修改跳转页面
    showDrawer=(row)=>{
        const {  preSymptomType:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"preSymptomType/setDrawerVisible",
            payload:!drawerVisible,
        })
        // if(row.id){
        //     dispatch({
        //         type:'symptomType/getSymptomTypeById',
        //         payload:{
        //             id:row.id,
        //             orgId:row.orgId,
        //         },
        //     })
        // }
    }
      columns=[
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`,
            width: "10%",
        },
        {
            title: '症状名',
            dataIndex: 'symptomName',
            width: "25%",
        },
        {
            title: '处方',
            dataIndex: 'parentId',
            width: "10%",
            render: (text,row) => (
                <div  style={{color:text==1?"green":"red"}}>
                   {text==1?"有":"失"}
                </div>
            ),
        },
        {
            title: '子症状',
            dataIndex: 'parentId',
            width: "10%",
            render: (text,row) => (
                <div style={{color:text>1?"green":"red"}}>
                   {text>1?"有效":"失效"}
                </div>
            ),
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            width: "15%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        {
            title: '修改时间',
            dataIndex: 'modifiedTime',
            width: "15%",
            render:(text,row) =>(
                <div>
                    {moment(text).format('YYYY-MM-DD HH:mm:ss')}
                </div>
            ),
        },
        {
            title: '操作',
            width: "15%",
            render: (text,row) => (
                <div>
                   <Button  type="primary" icon={'edit'}onClick={this.showDrawer.bind(this,row)} />
                </div>
            ),
        },
    ]
    render() {


        const { preSymptomType: { data, selectedRows,preRow, deleteDisabled, msg, selectedRowKeys, treeList,DescRow }, 
        form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
        const treeNodeLists = [treeList]
    
        return(

            <Layout>
                {/* 右边菜单 */}
                 <Sider width={300}  style={{ background: '#fff' }}>
                     
                 <div>

                 <div className={styles.titleSeting}>
                    症状菜单
                </div>
                <hr></hr>
                        <BaseDicTree
                            onSelect={this.onSelect}
                            treeList={treeNodeLists}
                            defaultExpandedKeys={[treeNodeLists[0].id]}
                        />
                 </div>
                 
                 <Modal
                    title="处方信息"
                    visible={this.state.visible}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                    okButtonProps={{ disabled: false }}
                    cancelButtonProps={{ disabled: false }}
                    >
                  
                  {/* <Form  labelCol={{ span: 8 }} wrapperCol={{ span: 15 }} onSubmit={this.handleSubmit} >
                  <div className={styles.titleSeting}>处方详情</div>
                    <br/><br/> 

                      <Form.Item label='项目描述' >
                        {getFieldDecorator('symptomDesc', { rules: [{ ...rules.required  }],initialValue:ok?data["symptomDesc"]:''
                        })(
                            <Input.TextArea rows={4}   />
                        )}
                    </Form.Item>  
                  </Form>    */}


                    
                    <TextArea rows={4} value= {DescRow.data?DescRow.data.symptomDesc:'无相关信息'}/>
                       
                    {/* </TextArea> */}
                </Modal>
                 </Sider>
                {/* 左边详细栏 */}
                <Layout>
                   <div>
                   <div className={styles.titleSeting}>
                    症状列表
                    </div>
                    <hr></hr>
                   <div className={configStyles.content} >
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
                        <Button type="primary" icon={'plus'} title="添加" />&nbsp;
                        <Button type="primary" icon={'delete'}  title="删除" />
                    </div>
                </div>
                         <div>
                               {/* 分页表格 */}
                            <StandardTable
                            selectedRows={selectedRows}
                            loading={loading}
                            data={data}
                            columns={this.columns}
                            type='preSymptomType'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                              />
                         </div>

                   </div>
                                
                </Layout>

                <PreSymptomTypeDrawer/>
            </Layout>
           
        );
    }
}

export default PreSymptomType;

