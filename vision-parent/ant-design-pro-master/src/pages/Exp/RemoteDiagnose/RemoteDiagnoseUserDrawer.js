import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col,Layout,Comment, Avata, List,Tooltip } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import StandardTable from '@/components/StandardTable/indexNatice';
import cookie from 'react-cookies';
import moment from 'moment'
const { TextArea } = Input;
const Option = Select.Option;
const { Header, Footer, Sider, Content } = Layout;

//回复框
const CommentList = ({ comments }) => (

    <List
      dataSource={comments}
      header={`${comments.length} ${comments.length > 1 ? '条消息' : 'reply'}`}
      itemLayout="horizontal"
      renderItem={props => <Comment {...props} />}
    />
  );

  const Editor = ({ onChange, onSubmit, submitting, value }) => (
    <div>
      <Form.Item>
        <TextArea rows={4} onChange={onChange} value={value} />
      </Form.Item>
      <Form.Item>
        <Button htmlType="submit" loading={submitting} onClick={onSubmit} type="primary">
         发送
        </Button>
      </Form.Item>
    </div>
  );
@Form.create()
@connect(({remoteDiagnoseUser, loading }) => ({
    remoteDiagnoseUser,
    loading: loading.models.remoteDiagnoseUser,
}))
class RemoteDiagnoseUserDrawer extends Component{
    constructor(props) {
        super(props);
        this.state = { 
            flag:1,
            comments: [],
            submitting: false,
             value: '',
         };
    }


    showDrawer=()=>{
        const {remoteDiagnoseUser: { drawerVisible }, dispatch} = this.props;
      
        dispatch({
            type:'remoteDiagnoseUser/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'remoteDiagnoseUser/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    
    //  //表单验证成功后的回调函数
    //  handleSubmit = (e) => {
        
    //     e.preventDefault();
    //     this.props.form.validateFields((err, fieldsValue) => {
    //         if (!err) {
    //             const {remoteDiagnoseUser: {  sysDiaRow, selectedRows }, dispatch } = this.props;
    //             const { data, ok} = sysDiaRow;
    //             const formData = formatData(fieldsValue,"",data["id"]);
    //             //封装表单数据对象
    //             // const formData = formatData(fieldsValue);
    //             formData.append("organizationId",1)
    //             //发起请求
    //             formDataSubmit(dispatch,'remoteDiagnoseUser',formData);
              
    //             //关闭抽屉
    //             this.showDrawer();
    //         }
    //     });
    // }

    onChange=(e)=>{
        this.setState({
            flag:e.target.value,
        })
    }

    //回复框



    handleSubmit = (e) => {
     
      const {remoteDiagnoseUser: {  sysDiaRow, selectedRows }, dispatch } = this.props;
      const { data, ok} = sysDiaRow;
      const dataValue=this.state.value;
      const registerUserId=data[0].registerUserId;
        if (!this.state.value) {
        
          return;
        }
        
     
        this.setState({
          submitting: true,
        });
    
        // setTimeout(() => {
          
        //   this.setState({
        //     submitting: false,
        //     value: '',
        //     comments: [
        //       {
        //         author: 'Han Solo',
        //         avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        //         content: <p>{this.state.value}</p>,
        //         datetime: moment().fromNow(),
        //       },
        //       ...this.state.comments,
        //     ],
        //   });
        // }, 1000);


         if(data[0].registerUserId){
           dispatch({
               type:'remoteDiagnoseUser/getAddExpSymptomsDescribed',
                payload:{
                   registerUserId:registerUserId,
                   remoteDiagnoseId:data[0].remoteDiagnoseId,
                   symptomsDescribed:this.state.value,
                },
            })
        }
        
      };

      handleChange = e => {
      
        this.setState({
          value: e.target.value,
        });
      };


      //遍历查询的症状描述信息
      setexpdata =(data)=>{
        const {remoteDiagnoseUser: {  sysDiaRow, selectedRows,sysDiasStartRow }, dispatch } = this.props;
        

        if(sysDiasStartRow){
          dispatch({
            type:'remoteDiagnoseUser/getExpSymptomsDescribed',
             payload:{
                registerUserId:sysDiaRow.data[0].registerUserId,
                remoteDiagnoseId:sysDiaRow.data[0].remoteDiagnoseId,
                
             },
         })

        }

      
          const content = [];
          if(data){
            data.forEach( item=> {
            
              content.push({
               author: '用户',
                    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                    content: <p>{item.symptomsDescribed}</p>,
                    datetime: moment().fromNow(),
              })
            });
          }
         
          return content;
        
        
      }

        //遍历查询专家回复消息
        setexpertReplyData =(expertReplyRow)=>{
          const { data, ok} = expertReplyRow;
          const expData = [data];
          const expertReplyData = [];
          if(data){
            expData.forEach( item=> {
              expertReplyData.push({
                actions: [<span key="comment-list-reply-to-0">Reply to</span>],
                author: '专家',
                avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                content: (
                  <p>
                    {item.expertReply}
                  </p>
                ),
                datetime: (
                  <Tooltip
                    title={moment()
                      .subtract(1, 'days')
                      .format('YYYY-MM-DD HH:mm:ss')}
                  >
                    <span>
                      {moment()
                        .subtract(1, 'days')
                        .fromNow()}
                    </span>
                  </Tooltip>
                ),
              })
            });
          }
         
          
         
            return expertReplyData;
          
          
        }
  







    render(){
        const {form: {getFieldDecorator} ,remoteDiagnoseUser: { drawerVisible, sysDiaRow,expertReplyRow}, dispatch } = this.props;
        const { data, ok} = sysDiaRow;
        const {  submitting, value } = this.state;
       
        const comments = this.setexpdata(data);
        const expertReplyData = this.setexpertReplyData([expertReplyRow]);
        return(
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'70%'}
            >
            <div>
            <Layout>
                 <Sider width={700}  style={{ background: '#70f3ff' }}>
                  
                    <div>

                    <div className={styles.titleSeting}>
                    用户症状描述
                 </div>
                 <br></br>
                    {comments.length > 0 && <CommentList comments={comments} />}
                    <Comment
                            // avatar={
                            //     <Avatar
                            //     src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                            //     alt="Han Solo"
                            //     />
                            // }
                            content={
                                <Editor
                                onChange={this.handleChange}
                                onSubmit={this.handleSubmit}
                                submitting={submitting}
                                value={value}
                                />
                            }
                            />
                    </div>
               
                </Sider>




                 <Layout style={{ background: '#44cef6' }}>

                   <div>
                   <div className={styles.titleSeting}>
                    专家回复
                 </div>
                 <br></br>
                  


                 <List
                    className="comment-list"
                    header={`${expertReplyData.length} 条消息`}
                    itemLayout="horizontal"
                    dataSource={expertReplyData}
                    renderItem={item => (
                      <li>
                        <Comment
                          actions={item.actions}
                          author={item.author}
                          avatar={item.avatar}
                          content={item.content}
                          datetime={item.datetime}
                   />
      </li>
    )}
  />,
                   </div>
               
                 </Layout>
           </Layout>
            </div>
          </Drawer>
        );
    }

}

export default RemoteDiagnoseUserDrawer;