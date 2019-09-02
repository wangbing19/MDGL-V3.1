import React, { Component } from 'react';
import { Tree } from 'antd';
import {renderTreeNodes}  from './renderTreeNodes';

const {TreeNode} = Tree
const DirectoryTree = Tree.DirectoryTree;
class BaseDicTree extends React.Component {

  constructor(props) {
    super(props);
    this.state = { 
      checkedKeys:[],
    };
  }

  componentWillMount=()=>{
    const {dispatch,type,checkedKeys} = this.props;
    if(dispatch&&type){
      this.getTreeData(dispatch,type);
      this.clearData(dispatch,type);
    }
    if(checkedKeys){
      this.setState({checkedKeys:checkedKeys})
    }
    
  }
  //获取数结构数据
  getTreeData = (dispatch,type) =>{
    dispatch({
        type:type+'/tree',
    });
  }
  //清除关联数据
  clearData(dispatch,type){
    dispatch({
        type:type+'/setCheckTreeKeys',
        payload:[],
    });
    dispatch({
      type:type+'/clearQueryCriteria',
    });
  }
  
  //根据点击节点id查询数据
  getTableList = (parentId) =>{
    const {dispatch,type} = this.props;
    dispatch({
        type:type+'/fetch',
        payload:{
          parentId:parentId,
          page: 1,
          start: 0,
        }
    });
  }
  //点击函数
  onSelect = (key,e) => {
      const {onSelect} = this.props;
      if(onSelect){
        onSelect(key,e);
      }
  };
  //点击复选框触发
  onCheck = (key,e) => {
      const {dispatch,type,onCheck} = this.props;
      this.setState({
        checkedKeys:key,
      })
      dispatch({
          type:type+'/setCheckTreeKeys',
          payload:key,
      });
      if(onCheck){
        onCheck(key,e);
      }
  };
  
    //渲染tree
    renderTreeNodes = data => data.map((item) => {
        if (item.children) {//如果字节点存在的话就递归创建子节点
            //是否有下一个节点，有的话就是文件夹图标没有就是文件图标
            return (
                <TreeNode title={item.text} key={item.id} dataRef={item} value={item.id} isLeaf={item.leaf}>
                    {this.renderTreeNodes(item.children)}
                </TreeNode>
            );
        }else{
            return <TreeNode title={item.text}  value={item.id} key={item.id} isLeaf={item.leaf}  dataRef={item} />;
        }
    })

  render() {
    //treeList传回树结构，type（modal的namespace）
    const { treeList, dispatch, type, onSelect, defaultExpandAll, defaultExpandedKeys, expandedKeys, checkable, checkStrictly, checkTreeKeys } = this.props
    const { checkedKeys } = this.state;
    return (
      <DirectoryTree
        multiple
        onSelect={this.onSelect}
        onExpand={this.onExpand}
        defaultExpandAll={defaultExpandAll}
        // defaultExpandedKeys={defaultExpandedKeys}
        // expandedKeys={expandedKeys}
        checkable={checkable} //节点前添加 Checkbox 复选框
        onCheck={this.onCheck} //点击复选框触发
        checkStrictly={checkStrictly}//checkable 状态下节点选择完全受控（父子节点选中状态不再关联）
        checkedKeys={checkTreeKeys?checkTreeKeys:checkedKeys}//选中数据
      >
      {this.renderTreeNodes(treeList)}
      </DirectoryTree>
    );
  }
}
export default BaseDicTree;