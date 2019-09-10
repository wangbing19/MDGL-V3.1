import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip,Modal,DatePicker  } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import TrainerTimeDrawer from './TrainerTimeDrawer.js';
import {deleteData} from '@/utils/dataUtils';
const FormItem = Form.Item;
const Option = Select.Option;
@connect(({trainer, loading }) => ({
    trainer,
    loading: loading.models.trainer,
}))
//创建表单
@Form.create()
class Trainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: false,
             //对话框展示页面
           modalVisible:false,
           startValue: null,
           endValue: null,
           endOpen: false,

           datalist:{},

         };
    }
    
    //生命周期函数
    componentDidMount=()=>{
       
        this.getTrainer();
    }

    getTrainer=(value)=>{
       
        const { dispatch } = this.props;
        dispatch({
            type:"trainer/fetch",
            payload:{
                pageCurrent:1,
                orgId:1,
                pageSize:20,
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
            dataIndex: 'organizationName',
            width: "10%",
           
        },
        {
            title: '门店地址',
            dataIndex: 'organizationAddress',
            width: "10%",
        },
        {
            title: '训练师姓名',
            dataIndex: 'trainerName',
            width: "10%",
            
        },
        {
            title: '性别',
            dataIndex: 'trainerGender',
            width: "10%",
           
        },
        {
            title: '职位',
            dataIndex: 'post',
            width: "10%",
        },
        {
            title: '简历',
            dataIndex: 'description',
            width: "10%",
        },
        {
            title: ' 人数限制',
            dataIndex: 'numberPeople',
            width: "10%",
        },
        {
            title: ' 联系人',
            dataIndex: 'linkman',
            width: "5%",
        },
        {
            title: ' 联系人电话',
            dataIndex: 'phone',
            width: "10%",
        },
        {
            title: ' 状态',
            dataIndex: 'trainerState',
            width: "5%",
        },
        {
            title: '操作',
            width: "10%",
            render: (text,row) => (
                <div>
                 
                {/* {<Button type="primary"  size="small" >预约时间</Button> */}
               <Button type="primary" icon={'edit'} onClick={this.showDrawer.bind(this,row)}></Button>&nbsp; 
               <Button type="primary"  icon={'check-square'} onClick={this.showModal.bind(this,row)} ></Button>
                
                
                </div>
            ),
        },
    ]


       //添加修改跳转页面
       showDrawer=(row)=>{
        const {  trainer:{ drawerVisible },dispatch } =this.props;
        dispatch({
            type:"trainer/setDrawerVisible",
            payload:!drawerVisible,
        })
      
        if(row.id){
            
            dispatch({
                type:'trainer/getTrainerById',
                payload:{
                    id:row.id,
                    organizationId:row.organizationId,
                },
            })
        }
    }


    showModal = (row) => {
        this.setState({
            modalVisible: true,
        });
      };

    //对话框展示状态
    handleOk = () => {
      
        this.setState({ loading: true });
        setTimeout(() => {
          this.setState({ loading: false, modalVisible: false });
        }, 3000);
      }

      handleCancel = () => {
        this.setState({ modalVisible: false });
      };


    delete=()=>{
        const { dispatch ,trainer:{selectedRowKeys} } = this.props;
  
       deleteData(selectedRowKeys,'trainer',dispatch);//通过id删除数据
    }




     onChange = (value, dateString)=> {
     
        console.log('Selected Time: ', value);
        console.log('Formatted Selected Time: ', dateString);
      }
      
       onOk=(value) =>{

       
        this.setState(
            this.datalist=[...this.datalist,value._d]
        )
   
        console.log('onOk: ', value._d);
        console.log('this.datalist: ', this.datalist);
      }

   

    //时间控件方法
    disabledStartDate = startValue => {
        const { endValue } = this.state;
        if (!startValue || !endValue) {
          return false;
        }
        return startValue.valueOf() > endValue.valueOf();
      };
    
      disabledEndDate = endValue => {
        const { startValue } = this.state;
        if (!endValue || !startValue) {
          return false;
        }
        return endValue.valueOf() <= startValue.valueOf();
      };
    
      onChange = (field, value) => {
        this.setState({
          [field]: value,
        });
      };
    
      onStartChange = value => {
        this.onChange('startValue', value);
      };
    
      onEndChange = value => {
        this.onChange('endValue', value);
      };
    
      handleStartOpenChange = open => {
        if (!open) {
          this.setState({ endOpen: true });
        }
      };
    
      handleEndOpenChange = open => {
        this.setState({ endOpen: open });
      };
    render() {

        // const { trainer: { data }, loading, dispatch} = this.props;
        // const records = data.list;
    
        const { trainer: { data, selectedRows, deleteDisabled, msg, selectedRowKeys}, 
        
        form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
        const { modalVisible,startValue, endValue, endOpen } = this.state;
       
      
        return (
            <div>

                <div>
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
                            <Button type="primary" icon={'edit'}  >预约时间设置</Button>
                            
                            <hr/>
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
                            type='trainer'
                            queryFormData={getFieldsValue()}
                            dispatch={dispatch}
                            deleteDisabled={deleteDisabled}
                            selectedRowKeys={selectedRowKeys}
                    /> 
               
                    <TrainerTimeDrawer/> 
                </div>

                <div>
         
        <Modal
          visible={modalVisible}
          title="设置预约时间"
          onOk={this.handleOk}
          onCancel={this.handleCancel}
          footer={[

            <Button type="primary" icon={'plus'} onClick={this.selectTimeAdd} title="添加" />,
            <Button key="back" onClick={this.handleCancel}>
              Return
            </Button>,
            <Button key="submit" type="primary" loading={loading} onClick={this.handleOk}>
              Submit
            </Button>,

           
          ]}
        >
          <div>
                    {/* {
                        this.state.listData.map(function (value) {
                            return  <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
                        })
                    } */}
            <div>
            <span>预约时间设置一：</span>
            <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
            </div>

            <div>
            <span>预约时间设置二：</span>
            <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
            </div>

            <div>
            <span>预约时间设置三：</span>
            <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
            </div>
            <div>
            <span>预约时间设置四：</span>
            <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
            </div>
            <div>
            <span>预约时间设置五：</span>
            <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
            </div>
            <div>
            <span>预约时间设置六：</span>
            <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
            </div>
            <div>
            <span>预约时间设置七：</span>
            <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
            </div>
            <div>
            <span>预约时间设置八：</span>
            <DatePicker showTime placeholder="Select Time" onChange={this.onChange} onOk={this.onOk} />
            </div>
          <br/>
      
          </div>
        </Modal>
                </div>
           
            </div>
        )
    }
}

export default Trainer;