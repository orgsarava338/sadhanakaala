import googleSvgIcon from "@/assets/icons/google.svg"
import useAuth from "@/auth/useAuth";
import Button from "./Button";
import { useNavigate } from "react-router-dom";

export default function GoogleLoginButton() {
  const { loginWithGoogle, loading } = useAuth();
  const navigate = useNavigate();

  const loginHandler = async () => {
    await loginWithGoogle();
    navigate("/dashboard");
  }

  return (
    <Button onClick={loginHandler} disabled={loading}>
        <img src={googleSvgIcon} className="w-5 h-5"/>
        Continue with Google
    </Button>
  );
};
