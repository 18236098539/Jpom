export interface IPageQuery {
  page: number
  limit: number
  total: number
  [key: string]: any
}
export interface SystemType {
  disabledCaptcha: bollean
  disabledGuide: bollean
  inDocker: bollean
  loginTitle: string
  name: string
  notificationPlacement: string
  routerBase: string
  subTitle: string
}

export interface GlobalWindow {
  routerBase: string
  apiTimeout: string
  uploadFileSliceSize: string
  uploadFileConcurrent: string
  oauth2Provide: string
  transportEncryption: string
}
