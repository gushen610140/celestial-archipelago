export class UserView {
  id: string;
  nickname: string;
  avatar: string;
  email: string;

  constructor() {
    this.id = "123";
    this.nickname = "未命名用户";
    this.avatar = "http://127.0.0.1:5577/static/common/default_avatar.jpg";
    this.email = "234@123.com";
  }
}
