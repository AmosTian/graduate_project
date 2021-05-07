import React from 'react';
import {Card, Checkbox, Form, Input, Modal, Select} from "antd";
import {connect} from "dva";
import PicturesWall from "../Utils/PicturesWall";

const FormItem = Form.Item;
const InputGroup = Input.Group;
const CheckboxGroup = Checkbox.Group;
const { TextArea } = Input;

const formItemLayout = {
  labelCol: {
    xs: { span: 24 },
    sm: { span: 7 },
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 12 },
    md: { span: 10 },
  },
};

const paymentMethod = [
  "",
  "付一押一",
  "付三押一",
  "付六押一",
  "年付押一",
  "其他"
]

const decoration = [
  "",
  "精装",
  "简装",
  "毛坯"
]

const rentMethod = [
  "",
  "整租",
  "合租"
]

const time = [
  "",
  "上午",
  "中午",
  "下午",
  "晚上",
  "全天"
]

const facilities = [
  "",
  "水",
  "电",
  "煤气/天然气",
  "暖气",
  "有线电视",
  "宽带",
  "电梯",
  "车位/车库",
  "地下室/储藏室"
]

function isChinese(temp){
  const re=/^[\u3220-\uFA29]+$/;
  if (re.test(temp))
    return true ;
  return false;
}

@connect()
@Form.create()/* 只有标注了 @Form.create() Form中的元素才可被封装 */
class EditResource extends React.Component{

  constructor(props){
    super(props);
    this.state={
      visible:false,
      pics:new Set()
    };
  }

  /* 显示编辑弹窗 */
  showModal = () => {
    this.setState({
      visible: true
    });
  };

  /* 隐藏编辑弹窗 */
  handleCancel = () => {
    this.setState({
      visible: false,
    });
  };

  handleSave = () => {
    const { dispatch, form, record } = this.props;
    form.validateFieldsAndScroll((err, values) => {

      if (!err) {
        // 房源id
        values.id = record.id;

        // 看房时间
        if(isChinese(values.time)){
          for (let i = 1; i < time.length; i++) {
            if(time[i]==values.time)
              values.time=i;
          }
        }

        // 支付方式
        if(isChinese(values.paymentMethod)){
          for (let i = 1; i < paymentMethod.length; i++) {
            if(paymentMethod[i]==values.paymentMethod)
              values.paymentMethod=i;
          }
        }

        // rentMethod
        if(isChinese(values.rentMethod)){
          for (let i = 1; i < rentMethod.length; i++) {
            if(rentMethod[i]==values.rentMethod)
              values.rentMethod=i;
          }
        }

        // decoration
        if(isChinese(values.decoration)){
          for (let i = 1; i < decoration.length; i++) {
            if(decoration[i]==values.decoration)
              values.decoration=i;
          }
        }

        if(values.floor_1 && values.floor_2){
          values.floor = `${values.floor_1  }/${  values.floor_2}`;
        }

        // 周边设施
        if(values.facilities){
          values.facilities = values.facilities.join(",");
        }

        // 楼栋信息
        values.buildingNum = record.buildingNum;
        values.buildingUnit = record.buildingUnit;
        values.buildingFloorNum = record.buildingFloorNum;
        delete values.building;

        // 照片
        if(this.state.pics.size > 0){
          values.pic = [...this.state.pics].join(',');
        }else if(this.state.pics.size == 0){
          values.pic = "";
        }
        else{
          values.pic = record.pic;
        }

        dispatch({
          type: 'house/updateHouseForm',
          payload: values,
        });

        setTimeout(()=>{
          this.handleCancel();
          this.props.reload();
        },500)

      }
    });

  };

  handleFileList = (obj)=>{
    const pics = new Set();
    obj.forEach((v, k) => {
      if(v.response){
        pics.add(v.response.name);
      }
      if(v.url){
        pics.add(v.url);
      }
    });

    this.setState({
      pics
    })
  }

  render(){

    const {record} = this.props;
    const {
      form: { getFieldDecorator }
    } = this.props;

    return (
      <React.Fragment>
        <a onClick={() => {this.showModal()}}>编辑</a>
        <Modal
          title="编辑"
          width={750}
          visible={this.state.visible}
          onOk={()=>{this.handleSave()}}
          onCancel={()=>{this.handleCancel()}}
          destroyOnClose
        >
          <div style={{ overflowY:'auto'}}>
            <Form hideRequiredMark style={{ marginTop: 8 }}>
              <Card bordered={false} title="出租信息">
                <FormItem {...formItemLayout} label="房源信息">
                  {getFieldDecorator('title',{initialValue:record.title  ,rules:[{ required: true, message:"此项为必填项" }]})(<Input style={{ width: '100%' }} disabled={false} />)}
                </FormItem>
                <FormItem {...formItemLayout} label="联系人">
                  {getFieldDecorator('contact',{initialValue:record.contact  ,rules:[{ required: true, message:"此项为必填项" }]})(<Input style={{ width: '100%' }} />)}
                </FormItem>
                <FormItem {...formItemLayout} label="联系方式">
                  {getFieldDecorator('mobile',{initialValue:record.mobile  ,rules:[{ required: true, message:"此项为必填项" }]})(<Input style={{ width: '100%' }} />)}
                </FormItem>
                <FormItem {...formItemLayout} label="看房时间">
                  {getFieldDecorator('time',{initialValue:time[record.time],rules:[{ required: true, message:"此项为必填项" }]})
                  (
                    <Select onSelect={record.time} style={{ width: '30%' }}>
                      <Option value="1">上午</Option>
                      <Option value="2">中午</Option>
                      <Option value="3">下午</Option>
                      <Option value="4">晚上</Option>
                      <Option value="5">全天</Option>
                    </Select>
                  )}
                </FormItem>
                <FormItem {...formItemLayout} label="租金">
                  <InputGroup compact>
                    {getFieldDecorator('rent',{initialValue:record.rent ,rules:[{ required: true, message:"此项为必填项" }]})(<Input style={{ width: '50%' }} addonAfter="元/月" />)}
                  </InputGroup>
                </FormItem>
                <FormItem {...formItemLayout} label="物业费">
                  <InputGroup compact>
                    {getFieldDecorator('propertyCost',{initialValue:record.propertyCost ,rules:[{ required: true, message:"此项为必填项" }]})(<Input style={{ width: '50%' }} addonAfter="元/月" />)}
                  </InputGroup>
                </FormItem>
                <FormItem {...formItemLayout} label="支付方式">
                  {getFieldDecorator('paymentMethod',{initialValue:paymentMethod[record.paymentMethod],rules:[{ required: true, message:"此项为必填项" }]})
                  (
                    <Select onSelect={record.paymentMethod} style={{ width: '50%' }}>
                      <Option value="1">付一押一</Option>
                      <Option value="2">付三押一</Option>
                      <Option value="3">付六押一</Option>
                      <Option value="4">年付押一</Option>
                      <Option value="5">其它</Option>
                    </Select>
                  )}
                </FormItem>
                <FormItem {...formItemLayout} label="租赁方式">
                  {getFieldDecorator('rentMethod',{initialValue:rentMethod[record.rentMethod],rules:[{ required: true, message:"此项为必填项" }]})
                  (
                    <Select style={{ width: '50%' }}>
                      <Option value="1">整租</Option>
                      <Option value="2">合租</Option>
                    </Select>
                  )}
                </FormItem>
              </Card>

              <Card bordered={false} title="房源信息">
                <FormItem {...formItemLayout} label="建筑面积">
                  <InputGroup compact>
                    {getFieldDecorator('coveredArea',{initialValue:record.coveredArea,rules:[{ required: true, message:"此项为必填项" }]})(<Input style={{ width: '40%' }} addonAfter="平米" />)}
                  </InputGroup>
                </FormItem>
                <FormItem {...formItemLayout} label="使用面积">
                  <InputGroup compact>
                    {getFieldDecorator('useArea',{initialValue:record.useArea,rules:[{ required: true, message:"此项为必填项" }]})(<Input style={{ width: '40%' }} addonAfter="平米" />)}
                  </InputGroup>
                </FormItem>
                <FormItem {...formItemLayout} label="楼栋">
                  <InputGroup compact>
                    {getFieldDecorator('building',{initialValue:`${record.buildingNum}栋${record.buildingUnit}单元${record.buildingFloorNum}号`,rules:[{ required: true, message:"此项为必填项" }]})(<Input disabled style={{ width: '55%' }} />)}
                  </InputGroup>
                </FormItem>
                <FormItem {...formItemLayout} label="楼层">
                  <InputGroup compact>
                    {getFieldDecorator('floor_1',{initialValue:record.floor.toString().split('/')[0],rules:[{ required: true, message:"此项为必填项" }]})(<Input disabled style={{ width: '45%' }} addonBefore="第" addonAfter="层" />)}
                    {getFieldDecorator('floor_2',{initialValue:record.floor.toString().split('/')[1],rules:[{ required: true, message:"此项为必填项" }]})(<Input disabled style={{ width: '45%'}} addonBefore="总" addonAfter="层" />)}
                  </InputGroup>
                </FormItem>
                <FormItem {...formItemLayout} label="朝向">
                  {getFieldDecorator('orientation',{initialValue:record.orientation,rules:[{ required: true, message:"此项为必填项"}]})
                  (
                    <Select disabled style={{ width: '20%' }}>
                      <Option value="南">南</Option>
                      <Option value="北">北</Option>
                      <Option value="东">东</Option>
                      <Option value="西">西</Option>
                    </Select>
                  )}
                </FormItem>
                <FormItem {...formItemLayout} label="户型">
                  <InputGroup compact>
                    {getFieldDecorator('houseType',{initialValue:record.houseType ,rules:[{ required: true, message:"此项为必填项" }]})(<Input disabled style={{ width: '55%' }} />)}
                  </InputGroup>
                </FormItem>
                <FormItem {...formItemLayout} label="装修">
                  {getFieldDecorator('decoration',{initialValue:decoration[record.decoration],rules:[{ required: true, message:"此项为必填项" }]})
                  (
                    <Select style={{ width: '35%' }}>
                      <Option value="1">精装</Option>
                      <Option value="2">简装</Option>
                      <Option value="3">毛坯</Option>
                    </Select>
                  )}
                </FormItem>
                <FormItem {...formItemLayout} label="配套设施">
                  {getFieldDecorator('facilities',{initialValue:record.facilities.split(','),rules:[{ required: true, message:"此项为必填项" }]})
                  (
                    <CheckboxGroup options={[
                      { label: '水', value: '1' },
                      { label: '电', value: '2' },
                      { label: '煤气/天然气', value: '3' },
                      { label: '暖气', value: '4' },
                      { label: '有线电视', value: '5' },
                      { label: '宽带', value: '6' },
                      { label: '电梯', value: '7' },
                      { label: '车位/车库', value: '8' },
                      { label: '地下室/储藏室', value: '9' }
                    ]}
                    />
                  )}
                </FormItem>
              </Card>

              <Card bordered={false} title="图片信息">
                <FormItem {...formItemLayout} label="房源描述">
                  {getFieldDecorator('houseDesc',{initialValue:record.houseDesc,rules:[{ required: false}]})
                  (
                    <TextArea autosize={{ minRows: 4, maxRows: 10 }} />
                  )}
                  <span>请勿填写联系方式或与房源无关信息以及图片、链接或名牌、优秀、顶级、全网首发、零距离、回报率等词汇。</span>
                </FormItem>
                <FormItem {...formItemLayout} label="上传室内图">
                  <PicturesWall value={record.pic} handleFileList={this.handleFileList.bind(this)} fileList={record.pic} />
                </FormItem>
              </Card>
            </Form>
          </div>

        </Modal>
      </React.Fragment>
    )
  }

}

export default EditResource;
