import React from 'react'; //第一步，导入React
class LifeCycle extends React.Component {
    constructor(props) {
        super(props);
        //构造方法
        console.log("constructor()");
    }

    componentDidMount() {
        //组件挂载后调用
        console.log("componentDidMount()");
    }

    componentWillUnmount() {
        //在组件从 DOM 中移除之前立刻被调用。
        console.log("componentWillUnmount()");
    }

    componentDidUpdate() {
        //在组件完成更新后立即调用。在初始化时不会被调用。
        console.log("componentDidUpdate()");
    }
    shouldComponentUpdate(nextProps, nextState){
        // 每当this.props或this.state有变化，在render方法执行之前，就会调用这个方法。
        // 该方法返回一个布尔值，表示是否应该继续执行render方法，即如果返回false，UI 就不会更新，默认返回true。
        // 组件挂载时，render方法的第一次执行，不会调用这个方法。
        console.log("shouldComponentUpdate()");
    }

    render() {
        return (
            <div>
                <h1>React Life Cycle!</h1>
            </div>
        );
    }
}

export default LifeCycle;