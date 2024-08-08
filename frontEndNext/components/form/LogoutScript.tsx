"use client"
import React, {useCallback, useEffect, useState} from "react";
import {useRouter} from "next/navigation";
import BoxMessage from "@/components/BoxMessage";
import {useApi} from "@/util/FetchBackEnd";

interface LogoutScript {

}


interface LogOutResponse {
    goodByeMessage: string;
}


const LogoutScript: React.FC<LogoutScript> = () => {
    const {data, error, fetchFromApi} = useApi<LogOutResponse>(); // Utilisez useApi pour obtenir fetchFromApi
    const router = useRouter();
    const [message, setMessage] = useState<string>('');
    const [logoutDone, setLogoutDone] = useState(false);


    const handleProfile = useCallback(async () => {
            setLogoutDone(true)
            await fetchFromApi<LogOutResponse>('http://localhost:8080/api/fr/auth/logout', {method: 'GET'});
            localStorage.removeItem("jwtToken");
        },
        [fetchFromApi]);

    useEffect(() => {
        if (!logoutDone) {
            handleProfile();
        }
    }, [handleProfile, logoutDone]);

    useEffect(() => {
        if (data) {
            setMessage(data?.goodByeMessage as string || "");
        }
    }, [data]);

    const redirect = () => {
        localStorage.removeItem("jwtToken");
        router.push("/auth/login");

    }

    return (
        <BoxMessage className={"flex !h-10 justify-around w-full sm:w-1/3"} message={message} error={error}
                    onTimeOut={redirect}></BoxMessage>
    )
}

export default LogoutScript;