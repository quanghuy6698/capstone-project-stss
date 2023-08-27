/* tslint:disable */
export interface IConfig {
  apiUrl: {
    baseURI: string;
    tradeX: {
      clientId?: string;
      clientSecret?: string;
      socketCluster?: {
        hostname?: string;
        secure?: boolean;
        port?: number;
        codecEngine: any;
        authTokenName?: string;
        autoReconnect?: boolean;
      };
    };
    domain: {
      [s: string]: {
        clientId?: string;
        clientSecret?: string;
        socketCluster?: {
          hostname?: string;
          secure?: boolean;
          port?: number;
          codecEngine: any;
          authTokenName?: string;
          autoReconnect?: boolean;
        };
      };
    };
  };
  regex: {
    password: RegExp;
    username: RegExp;
    email: RegExp;
    phoneNumber: RegExp;
    number: RegExp;
    orderPasswordKBSV: RegExp;
  };
}

let config: IConfig = {
  apiUrl: {
    baseURI: 'http://192.168.1.119:8090/',
    tradeX: {},
    domain: {},
  },
  regex: {
    password: /^[-\w]{8,32}$/,
    username: /^[-\w]{8,32}$/,
    // eslint-disable-next-line
    email: /^[a-z][a-z0-9_\.]{5,32}@[a-z0-9]{2,}(\.[a-z0-9]{2,4}){1,2}$/,
    phoneNumber: /^\d{10}$/,
    number: /^[0-9]*$/,
    orderPasswordKBSV: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{7,}$/,
  },
};

export default config;
