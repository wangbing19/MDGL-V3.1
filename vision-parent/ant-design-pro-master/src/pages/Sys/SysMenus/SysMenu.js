import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { TreeSelect,Layout,Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip  } from 'antd';
import styles from '@/less/config.less';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import SysMenuDrawer from './SysMenuDrawer.js';
import {deleteData} from '@/utils/dataUtils';
const { Header, Footer, Sider, Content } = Layout;

const { SHOW_PARENT } = TreeSelect;

// const treeData = [
//   {
//     title: 'Node1',
//     value: '0-0',
//     key: '0-0',
//     children: [
//       {
//         title: 'Child Node1',
//         value: '0-0-0',
//         key: '0-0-0',
//       },
//     ],
//   },
//   {
//     title: 'Node2',
//     value: '0-1',
//     key: '0-1',
//     children: [
//       {
//         title: 'Child Node3',
//         value: '0-1-0',
//         key: '0-1-0',
//       },
//       {
//         title: 'Child Node4',
//         value: '0-1-1',
//         key: '0-1-1',
//       },
//       {
//         title: 'Child Node5',
//         value: '0-1-2',
//         key: '0-1-2',
//       },
//     ],
//   },
// ];

const FormItem = Form.Item;
const Option = Select.Option;
@connect(({sysMenu, loading }) => ({
  sysMenu,
  loading: loading.models.sysMenu,
}))
@Form.create()
class SysMenus extends React.Component {
  constructor(props) {
    super(props);
    this.state = {  
      
      value: ['所有菜单'],
      menusValue:{
        title: '',
        value: '',
        key: '',
        children:[],
      }
  };
}
  // state = {
   // value: ['0-0-0'],
  // };

        onChange = value => {
          console.log('onChange ', value);

          const { dispatch } = this.props;
          dispatch({
              type:"sysMenu/findMenusOne",
              payload:{
                  id:value
              }
          });
          this.setState({ value });
        };


        //生命周期函数
        componentDidMount=()=>{
            
          this.getMenus();
      }

      getMenus=(value)=>{
      const { dispatch } = this.props;
      dispatch({
          type:"sysMenu/getMenus",
          payload:{
            
              ...value,
          }
      });

      dispatch({
        type:"sysMenu/fetch",
        payload:{
            pageCurrent:1,
            pageSize:20,
            ...value,
        }
    })
    };

      sysdata=(menusValues)=>{
          this.setState(
            this.state.menusValue=menusValues.data
          )
      };
      
    //添加修改跳转页面
    showDrawer=(row)=>{
      const {  sysMenu:{ drawerVisible },dispatch } =this.props;
      
      dispatch({
          type:"sysMenu/setDrawerVisible",
          payload:!drawerVisible,
      })
       if(row.id){
           dispatch({
              type:'sysMenu/findMenusOne',
              payload:{
                   id:row.id,
                  
               },
           })
       }
    };

      //删除
      delete=()=>{
        const { dispatch ,sysMenu:{selectedRowKeys} } = this.props;
        deleteData(selectedRowKeys,'sysMenu',dispatch);//通过id删除数据
    };
  columns=[
    {
        title: '菜单名',
        dataIndex: 'name',
        width: "10%",
      
       
    },
    {
        title: '资源路径',
        dataIndex: 'url',
        width: "25%",
      
    },
  
    {
        title: '排序',
        dataIndex: 'sort',
        width: "5%",
       
    },
    {
        title: '授权(如：user:create)',
        dataIndex: 'permission',
        width: "25%",
    },
    {
      title: '类型',
      dataIndex: 'type',
      width: "10%",
      
  },
    {
      title: '备注',
      dataIndex: 'note',
      width: "10%",
  },
    
    {
        title: '操作',
        width: "15%",
        render: (text,row) => (
            <div>
             
            {/* {<Button type="primary"  size="small" >预约时间</Button> */}
            <Button type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}></Button>
            
            </div>
        ),
    },
]

  render() {

    const {sysMenu: {  treeData,sysMenuRow, selectedRows, deleteDisabled, msg, selectedRowKeys,data},
    form: { getFieldDecorator,getFieldsValue }, loading,dispatch } = this.props;
 
   
    // {
    //   this.sysdata(sysMenuRow);
    // }

    const tProps = {
      treeData,
      value: this.state.value,
      onChange: this.onChange,
      treeCheckable: false,
      showCheckedStrategy: SHOW_PARENT,
      searchPlaceholder: '菜单管理',
      style: {
        width: 300,
      },
    };
   return(
       <div>
         <div className={styles.titleSeting}>
           菜单列表
         </div>
         <br></br>
           <Layout>
                 <Sider width={300}  style={{ background: '#fff' }}><TreeSelect {...tProps} /></Sider>
                 <Layout>

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
                        <Button type="primary" icon={'plus'}  onClick={this.showDrawer} title="添加" />&nbsp;
                        <Button type="primary" icon={'delete'}  onClick={this.delete} disabled={deleteDisabled} title="删除" />
                    </div>
                </div>

                  <div>
                  <StandardTable
                    selectedRows={selectedRows}
                    loading={loading}
                    data={data}
                    columns={this.columns}
                    type='sysMenu'
                    queryFormData={getFieldsValue()}
                    dispatch={dispatch}
                    deleteDisabled={deleteDisabled}
                    selectedRowKeys={selectedRowKeys}/> 
                   
  
                  </div> 
                  
                  <SysMenuDrawer/>
                 </Layout>
           </Layout>
       </div>
   );
  }
}
export default SysMenus;