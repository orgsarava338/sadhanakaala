enum Role {
  USER,
  ADMIN,
}

interface ApiUser {
  uid: string;
  roles: Role[];
  active: boolean;
  lastLoginAt: String;
  createdAt: string;
}

interface OAuthUser {
  uid: string;
  email: string;
  name: string;
  photoURL: string;
}


interface User extends ApiUser, OAuthUser {}
