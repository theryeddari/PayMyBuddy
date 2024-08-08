"use client";
import React, {useEffect, useState} from "react";
import FieldInput from "@/components/FieldInput";
import SubmitButtonDynamic from "@/components/SubmitButton";
import BoxMessage from "@/components/BoxMessage";
import {useRouter} from "next/navigation";
import {useApi} from "@/util/FetchBackEnd";
import Link from "next/link";

interface LoginForm {
    children?: React.ReactNode;
    buttonText?: string;
}

interface SignInResponse {
    welcomingMessage: string;
}

const LoginForm: React.FC<LoginForm> = ({children, buttonText}) => {
    const {data, error, fetchFromApi,} = useApi<SignInResponse>(); // Utilisez useApi pour obtenir fetchFromApi
    const router = useRouter();

    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [responseIntoData, setResponseIntoData] = useState<string>("");
    const [isSubmitted, setIsSubmitted] = useState<boolean>(false);

    const handleSubmit = async (event: React.FormEvent<HTMLElement>) => {
        event.preventDefault()
        setResponseIntoData("");
        setIsSubmitted(true);
        await fetchFromApi('http://localhost:8080/api/fr/auth/signin', {
            method: 'POST',
            body: JSON.stringify({email, password}),
        });
    };

    const handleInputChange = () => {
        setIsSubmitted(false); // Réinitialiser isSubmitted si l'utilisateur commence à taper après la soumission
    };

    useEffect(() => {
        setResponseIntoData(data?.welcomingMessage as string || "");
    }, [data]);


    const redirect = () => {
        router.push('/dashboard/profile');
    }

    return (
        <div className={"h-2/3 w-full flex flex-col items-stretch justify-around"}>
            <form onSubmit={handleSubmit} className="flex flex-col w-full space-y-6">
                {isSubmitted && <BoxMessage onTimeOut={redirect} message={responseIntoData} error={error}></BoxMessage>}
                {children}
                <FieldInput type="email" id="email" name="email" placeholder="Mail" required value={email}
                            onChange={(e) => {
                                setEmail(e.target.value);
                                handleInputChange();
                            }}/>
                <FieldInput type="password" id="password" name="password" placeholder="Mot de passe" value={password}
                            onChange={(e) => {
                                setPassword(e.target.value);
                                handleInputChange()
                            }} required/>
                <SubmitButtonDynamic id="login" name="login"
                                     className="!bg-blue-400 hover:!bg-blue-600">{buttonText}</SubmitButtonDynamic>
            </form>
            <Link className={"max-w-min text-white rounded-xl text-nowrap p-2 bg-blue-400 mt-10 hover:bg-blue-600"}
                  href={"/auth/signup"}>Inscription</Link>
        </div>
    );
};

export default LoginForm;