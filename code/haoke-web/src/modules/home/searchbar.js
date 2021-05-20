import React from 'react';
// import { Icon} from 'semantic-ui-react'
import { Icon,Item,Pagination,Label,Container } from 'semantic-ui-react';
import "./search.css";


class SearchBar extends React.Component {

    handlePageChange = (e, { activePage }) =>{
        this.props.searchPage(null,{
            page:activePage
        });
    }

    hideSearchBar = () => {
        this.props.hideSearchBar();
    }

    handleHotSearch = (e,data) =>{
        this.props.searchPage(null,{value:data.children});
    }

    render() {
        return (
            <div className = 'search-bar' >
                <Icon onClick={this.hideSearchBar} name = 'angle left' size = 'large'/>
                {
                    this.props.totalPage > 1?(
                        <Pagination
                            defaultActivePage={1}
                            firstItem={null}
                            lastItem={null}
                            totalPages={this.props.totalPage}
                            onPageChange={this.handlePageChange}
                        />
                    ):null
                }
                {
                    this.props.hotWord ? (
                        <Container>搜索结果较少，建议搜索：
                            <br/>
                            <span>
                                {
                                    this.props.hotWord.map(item => {
                                        return (
                                            <Label onClick={this.handleHotSearch}>{item}</Label>
                                        )
                                    })
                                }
                                </span>
                        </Container>
                    ): null
                }
                <div className = "search-bar-content">
                    <Item.Group divided unstackable>
                        {
                            this.props.searchData.map(item => {
                                return (
                                    <Item key={item.id}>
                                        <Item.Image
                                            src={"https://haoke-1257323542.cos.ap-beijing.myqcloud.com/tylj-images/"
                                            + item.pic.split(",")[0]}/>
                                        <Item.Content>
                                            <Item.Header>
                                                <div className='house-title' dangerouslySetInnerHTML={{__html:item.title}}>
                                                </div>
                                            </Item.Header>
                                            <Item.Meta>
                                    <span className='cinema'>
{item.orientation}/{item.rentMethod}/{item.houseType}</span>
                                            </Item.Meta>
                                            <Item.Description>
                                                太原
                                            </Item.Description>
                                            <Item.Description>{item.rent}
                                            </Item.Description>
                                        </Item.Content>
                                    </Item>
                                )
                            })
                        }
                    </Item.Group>
                </div>
            </div>
        );
    }
}

export default SearchBar;


