// app/login/page.tsx
import AuthLayout from "@/app/auth/AuthLayout";
import React from "react";
import SignUpForm from "@/components/form/SignUpForm";

const LoginPage: React.FC = () => {
    return (
        <AuthLayout>
            <SignUpForm buttonText={"S'inscrire"}></SignUpForm>
        </AuthLayout>
    );
};

export default LoginPage;