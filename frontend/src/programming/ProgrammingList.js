import React, { Component } from 'react';
import { getAllProgrammings } from '../util/APIUtils';
import Programming from './Programming';
import LoadingIndicator  from '../common/LoadingIndicator';
import { PROGRAMMING_LIST_SIZE } from '../constants';
import { withRouter } from 'react-router-dom';

class ProgrammingList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            programmings: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadProgrammingList = this.loadProgrammingList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadProgrammingList(page = 0, size = PROGRAMMING_LIST_SIZE) {
        let promise = getAllProgrammings(page, size);

        if (!promise) {
            return null;
        }

        this.setState({
            isLoading: true
        });

        promise.then(response => {
            const programmings = this.state.programmings.slice();

            this.setState({
                programmings: programmings.concat(response),
                page: response.page,
                size: response.size,
                totalElements: response.totalElements,
                totalPages: response.totalPages,
                last: response.last,
                isLoading: false
            })
        }).catch(error => {
            this.setState({
                isLoading: false
            })
        })
    }

    componentDidMount() {
        this._mounted = true;
        this.loadProgrammingList();
    }

    componentWillUnmount () {
        this._mounted = false
    }

    setState(state, callback) {
        if (this._mounted) {
            super.setState(state, callback);
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(this.props.isAuthenticated !== prevProps.isAuthenticated) {
            // Reset State
            this.setState({
                programmings: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                isLoading: false
            });
            this.loadProgrammingList();
        }
    }

    handleLoadMore() {
        this.loadProgrammingList(this.state.page + 1);
    }

    render() {
        const programmingViews = [];
        this.state.programmings.forEach((programming, programmingIndex) =>{
            programmingViews.push(<Programming
                key={programming.id}
                programming={programming}
            />)
        });

        return (
            <div className="programmings-container">
                {programmingViews}
                {
                    !this.state.isLoading && this.state.programmings.length === 0 ? (
                        <div className="no-programmings-found">
                            <span>No Programmings Found.</span>
                        </div>
                    ): null
                }
                {
                    this.state.isLoading ?
                        <LoadingIndicator />: null
                }
            </div>
        );
    }
}

export default withRouter(ProgrammingList);
