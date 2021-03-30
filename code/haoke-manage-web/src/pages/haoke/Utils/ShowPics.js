import React from 'react';
import { Modal, Button, Carousel } from 'antd';

class ShowPics extends React.Component{
  info = () => {
    Modal.info({
      title: '',
      iconType:'false',
      width: '800px',
      centered:true,
      okText: "ok",
      content: (
        <div style={{ background: '#364d79',maxWidth:650, maxHeight: 400, lineHeight:400, textAlign:"center"}}>
          <Carousel autoplay autoplaySpeed={2000}>
            {
              this.props.pics.split(',').map((value,index) => <div>
                <img style={{ maxWidth:600 ,maxHeight:400, margin:"0 auto" }} src={value} />
              </div>)
            }
          </Carousel>
        </div>
      ),
      onOk() {},
    });
  };

  constructor(props){
      super(props);
      this.state={
        btnDisabled: !this.props.pics
    }
  }

  render() {
    return (
      <div>
        <Button disabled={this.state.btnDisabled} icon="picture" shape="circle" onClick={()=>{this.info()}} />
      </div>
    )
  }
}

export default ShowPics;
