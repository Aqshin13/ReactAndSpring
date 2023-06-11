import React, { Component } from "react";
import axios from "axios";

function getDisplayName(WrappedComponent) {
  return WrappedComponent.displayName || WrappedComponent.name || "Component";
}

export function withApiProgress(WrappedComponent, apiPath) {
  return class extends Component {
    static displayName = `ApiProgress(${getDisplayName(WrappedComponent)})`;
    // static displayName="ApiProgress("+getDisplayName(WrappedComponent)+")";

    state = {
      pendingApiCall: false,
    };

    componentDidMount() {
      //Sehife render olub bitdikden sonra ise dusur ve interceptorslar assign olunur
      //(Yeni axios'a interceptorlar set olunur)
      //Her requestde ise bu interceptorlar ise dusur

      this.requestInterceptor =  axios.interceptors.request.use((request) => {
        this.updateApiCallFor(request.url, true);
        return request;
      });

      this.responseInterceptor = axios.interceptors.response.use(
        (response) => {
          this.updateApiCallFor(response.config.url, false);
          return response;
        },
        (error) => {
          this.updateApiCallFor(error.config.url, false);
          throw error;
        }
      );
    }

    componentWillUnmount() {
      axios.interceptors.request.eject(this.requestInterceptor);
      axios.interceptors.response.eject(this.responseInterceptor);//interseptor silinir
    }

    updateApiCallFor = (url, inProgress) => {
      if (url === apiPath) {
        this.setState({ pendingApiCall: inProgress });
      }
    };

    render() {
      const { pendingApiCall } = this.state;
      return (
        <WrappedComponent pendingApiCall={pendingApiCall} {...this.props} />
      );
      // <div>{React.cloneElement(this.props.children, { pendingApiCall })}</div>

      //Eslinde subelementi clone edirem ve propsuna pendingApiCall otururem.
      //Yeni render olunan sey kopya elementdi
    }
  };
}
