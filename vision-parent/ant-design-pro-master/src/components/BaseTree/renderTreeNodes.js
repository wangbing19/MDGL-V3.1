import { Tree } from 'antd';
const {TreeNode} = Tree

export function renderTreeNodes(data){ data.map((item) => {
  if (item.childrens) {//如果字节点存在的话就递归创建子节点
          if(item.leaf){//是否有下一个节点，有的话就是文件夹图标没有就是文件图标
            return (
              <TreeNode title={item.text} key={item.id} dataRef={item} id={item.id} isLeaf>
                {renderTreeNodes(item.childrens)}
              </TreeNode>
            );
          }
          return (
            <TreeNode title={item.text} key={item.id} dataRef={item} id={item.id}>
              {renderTreeNodes(item.childrens)}
            </TreeNode>
          );
        }else{
          if(item.leaf){
            return <TreeNode title={item.text}  id={item.id} key={item.id} leaf={item.leaf}  dataRef={item} isLeaf/>;
          }
          return <TreeNode title={item.text}  id={item.id} key={item.id} leaf={item.leaf}  dataRef={item} />;
        }
      
      })
}