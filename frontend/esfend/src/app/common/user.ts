export class User {
  profileId?: string; // optional, backend will generate
  fullName: string;
  emailId: string;
  password: string;
  mobileNumber: number;
  gender: string;
  image: string;
  role: string = 'USER'; // default role

  constructor(
    fullName: string,
    emailId: string,
    password: string,
    mobileNumber: number,
    gender: string,
    image: string
  ) {
    this.fullName = fullName;
    this.emailId = emailId;
    this.password = password;
    this.mobileNumber = mobileNumber;
    this.gender = gender;
    this.image = image;
  }
}
