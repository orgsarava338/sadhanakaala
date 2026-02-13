enum Role {
  USER,
  ADMIN,
}

interface User {
  uid: string;
  email: string;
  name: string;
  photoURL: string;
  
  roles: Role[];
  active: boolean;
  lastLoginAt: String;
  createdAt: string;
}

interface ApiUser {
  uid: string;
  roles: Role[];
  active: boolean;
  lastLoginAt: String;
  createdAt: string;
}
