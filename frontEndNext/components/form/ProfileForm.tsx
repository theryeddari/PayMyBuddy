'use client'
import React, {useCallback, useEffect, useState} from "react";
import {useApi} from "@/util/FetchBackEnd";
import FieldInput from "@/components/FieldInput";
import SubmitButton from "@/components/SubmitButton";
import BoxMessage from "@/components/BoxMessage";

interface ProfileForm {
    children?: React.ReactNode;

}

interface ProfileClientResponse {
    username: string;
    email: string;
}

interface ProfileClientChangeResponse {
    messageSuccess: string;
}

const ProfileForm: React.FC<ProfileForm> = ({children}) => {

    const {data, error, fetchFromApi} = useApi<ProfileClientResponse, ProfileClientChangeResponse>();
    const {
        data: dataPostResponse,
        error: errorPostResponse,
        fetchFromApi: fetchFromApiPost
    } = useApi<ProfileClientChangeResponse>();


    const [username, setUsername] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>('');
    const [updateProfile, setUpdateProfile] = useState(true);
    const [message, setMessage] = useState<string>('');
    //recreer la méthode pour recupérer la page si elle change (normalement jamais)

    const loadProfile = useCallback(async () => {
        setUpdateProfile(false);
        await fetchFromApi('http://localhost:8080/api/fr/client/dashboard/profil', {
            method: 'GET'
        });
    }, [fetchFromApi])

    useEffect(() => {
        if (updateProfile) {
            loadProfile();
        }
    }, [loadProfile, updateProfile]);

    //vérifie data qui est recupéré par useApi via handlePRofil et modifie les variable username et Email si elles ont changer dans le data.
    useEffect(() => {
        if (data) {
            setUsername(data.username);
            setEmail(data.email);
        }
    }, [data]);

    useEffect(() => {
        if (dataPostResponse) {
            setMessage(dataPostResponse.messageSuccess)
            setUpdateProfile(true)
        }
    }, [dataPostResponse]);


    const handleProfileChange = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setMessage('')
        await fetchFromApiPost('http://localhost:8080/api/fr/client/dashboard/profil', {
            method: 'POST',
            body: JSON.stringify({username, email, password}),
        });
    };


    return (
        <form onSubmit={handleProfileChange}
              className=" w-full sm:w-1/3 pl-2 pr-2 flex sm:min-w-96 flex-col sm:flex-row gap-4">
            <div className={"w-full"}>
                <BoxMessage message={message} error={error || errorPostResponse}></BoxMessage>
                <FieldInput onChange={event => setUsername(event.target.value)} type={"text"} id={"username"}
                            name={"profile"} label={"Username"} value={username}
                            className={"w-full placeholder:font-bold"}></FieldInput>
                <FieldInput onChange={event => setEmail(event.target.value)} type={"email"} id={"mail"} name={"profile"}
                            label={"Mail"} value={email} className={"w-full placeholder:font-bold"}></FieldInput>
                <FieldInput required={true} onChange={event => setPassword(event.target.value)} type={"password"}
                            id={"password"} name={"profile"} label={"Mot de passe"} placeholder={"mot de passe"}
                            className={"w-full placeholder:font-bold"}></FieldInput>
            </div>
            <div className={"flex items-end"}>
                <SubmitButton id={"buton"} name={"buton"}
                              className={" h-12 sm:py-1.5 bg-orange-400 hover:bg-orange-600"}>Modifier</SubmitButton>
            </div>

        </form>
    )
};

export default ProfileForm;