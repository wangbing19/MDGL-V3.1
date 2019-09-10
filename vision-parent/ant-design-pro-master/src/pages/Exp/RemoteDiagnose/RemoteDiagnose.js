import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { TreeSelect,Layout,Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip  } from 'antd';
import styles from '@/less/config.less';
import StandardTable from '@/components/StandardTable/indexNatice';
const { Header, Footer, Sider, Content } = Layout;

const { SHOW_PARENT } = TreeSelect;
const FormItem = Form.Item;
const Option = Select.Option;
// const treeData = [
//     {
//       title: '成都温江店',
//       value: '12',
//       key: '1',
//       children: [
//         {
//           title: '汪兵',
//           value: '汪兵',
//           key: '0',
//         },
//         {
//           title: '汪洋',
//           value: '汪洋',
//           key: '1',
//         },
//       ],
//     },
//     {
//       title: '成都郫县店',
//       value: '成都郫县店',
//       key: '',
//       children: [
//         {
//           title: '李宏达',
//           value: '宏达',
//           key: '3',
//         },
        
//       ],
//     },
//   ];
  @connect(({remoteDiagnose, loading }) => ({
    remoteDiagnose,
    loading: loading.models.remoteDiagnose,
  }))
@Form.create()
class RemoteDiagnose extends React.Component {
    constructor(props) {
        super(props);
        this.state = {  
          
         value: undefined,
          
      };
    }

    onChange = value => {
        console.log(value);
        this.setState({ value });

        
      };


       //生命周期函数
   componentDidMount=()=>{
       
    this.getRemoteDiagnose();
    }

    getRemoteDiagnose=(value)=>{
      const { dispatch } = this.props;
      dispatch({
          type:"remoteDiagnose/getRemoteDiagnose",
          payload:{
            
              ...value,
          }
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
            dataIndex: 'name',
            width: "10%",
          
           
        },
        {
            title: '门店地址',
            dataIndex: 'url',
            width: "10%",
          
        },
        {
            title: '用户',
            dataIndex: 'type',
            width: "5%",
            
        },
        {
            title: '客户电话',
            dataIndex: 'sort',
            width: "10%",
           
        },
        {
            title: '选择专家',
            dataIndex: 'permission',
            width: "10%",
        },
        {
            title: '次数',
            dataIndex: 'permission',
            width: "5%",
        },
        {
            title: '签名',
            dataIndex: 'permission',
            width: "5%",
        },
        {
            title: '签名电话',
            dataIndex: 'permission',
            width: "10%",
        },
        {
            title: '创建时间',
            dataIndex: 'permission',
            width: "10%",
        },
        {
            title: '修改时间',
            dataIndex: 'permission',
            width: "10%",
        },
        {
            title: '状态',
            dataIndex: 'permission',
            width: "5%",
        },
        {
            title: '详情',
            dataIndex: 'permission',
            width: "5%",
        },
      
     
    ]

    render() {

      const {remoteDiagnose: {  treeData,sysDiaRow, selectedRows, deleteDisabled, msg, selectedRowKeys,},
      form: { getFieldDecorator,getFieldsValue }, loading,dispatch } = this.props;
     const { data, ok} = sysDiaRow;
  
        return(
            <div>



                  <div className={styles.titleSeting}>
                    专家诊断列表
                 </div>
                 <br></br>

                 
                 <Layout>

                 <Sider width={300}  style={{ background: '#e3f9fd' }}> 
                 
                 <TreeSelect
                    style={{ width: 300 }}
                    value={this.state.value}
                    dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                    treeData={treeData}
                  placeholder="请选择用户名"
                 treeDefaultExpandAll
                 onChange={this.onChange}
      />
      </Sider>
                     <Layout>

                     <Table   columns={this.columns}/> 
                     </Layout>
                 </Layout>
            </div>
        )
    }
}

export default RemoteDiagnose;