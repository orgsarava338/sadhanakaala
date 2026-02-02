interface User {
  id: string;
  name: string;
  email: string;
  streak: number;
}

interface LoginDTO {
  name: string;
  email: string;
  token: string;
}