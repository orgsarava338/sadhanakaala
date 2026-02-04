import GoogleLoginButton from "@/components/ui/button/GoogleLoginButton";

export default function LoginPage() {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-10 rounded-2xl shadow-md w-[380px] text-center">
        <h1 className="text-2xl font-bold mb-2">Welcome back 🙏</h1>
        <p className="text-gray-500 mb-8">
          Sign in to continue to SadhanaKaala
        </p>

        <GoogleLoginButton />

        <p className="text-xs text-gray-400 mt-6">
          By continuing, you agree to our Terms & Privacy Policy
        </p>
      </div>
    </div>
  );
};
