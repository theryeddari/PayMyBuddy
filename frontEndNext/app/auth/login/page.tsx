// app/login/page.tsx

import AuthLayout from "@/app/auth/AuthLayout";
import React from "react";
import LoginForm from "@/components/form/LoginForm";

const LoginPage: React.FC = () => {
    return (
        <AuthLayout>
            <LoginForm buttonText={"Se connecter"}></LoginForm>
        </AuthLayout>
    );
};

export default LoginPage;