// lib/api.ts
"use client"
import {useRouter} from "next/navigation";
import {useState} from "react";
import {ApiError} from "next/dist/server/api-utils";


export function useApi<T>(): {
    fetchFromApi: (endpoint: string, options: RequestInit) => Promise<() => void>;
    data: T | null;
    error: Error
} {
    const [data, setData] = useState<T | null>(null); // Changez la valeur initiale en null
    const [error, setError] = useState<Error>(null); // Changez la valeur initiale en null
    const router = useRouter();

    const fetchFromApi = async (endpoint: string, options: RequestInit) => {
        try {
            setError(null);
            setData(null);
            const jwtToken = localStorage.getItem("jwtToken");

            if (!options.headers) {
                options.headers = {};
            }
            (options.headers as HeadersInit)["Content-Type"] = `application/json`;

            if (jwtToken) {
                (options.headers as HeadersInit)["Authorization"] = `Bearer ${jwtToken}`;
            }

            const response = await fetch(endpoint, options);

            if (!response.ok) {
                if (response.status === 401) {
                    router.push('/auth/login'); // Redirection en cas de statut 401
                }
                const errorData = await response.text();
                throw new ApiError(response.status, errorData);
            }

            const authHeader = response.headers.get("Authorization");
            if (authHeader && authHeader.startsWith("Bearer ")) {
                const newJwtToken = authHeader.substring(7);
                localStorage.setItem("jwtToken", newJwtToken);
            }

            // Lire la réponse en texte brut pour diagnostic
            const responseText = await response.text();
            console.log("Response Text:", responseText); // Affichez le texte brut pour débogage

            // Parser la réponse en JSON

            const result = responseText ? JSON.parse(responseText) : null;
            setData(result);

        } catch (error) {
            let timer;
            if (error instanceof TypeError) {
                setError(new Error("Connection Server Error retrieve later."));
                timer = setTimeout(() => {
                    router.push('/auth/login');
                }, 2000);
            } else {
                setError(error)
            }


            return () => {
                clearTimeout(timer);
            }
        }
    };

    return {data, error, fetchFromApi};
}