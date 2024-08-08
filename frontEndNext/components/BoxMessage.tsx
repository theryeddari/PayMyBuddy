import React, {useEffect, useState} from "react";


interface BoxMessage {
    message?: string;
    error?: Error | null;
    children?: React.ReactNode;
    className?: string;
    onTimeOut?: () => void;
}

const BoxMessage: React.FC<BoxMessage> = ({message = "", children, className = "", error = null, onTimeOut}) => {
    const [visible, setVisible] = useState<string>('hidden');
    const [duration, setDuration] = useState<number>(0);
    const [onRedirection, setOnRedirection] = useState<boolean>(false);

    useEffect(() => {

            if (message.length > 0) {
                setVisible('visible bg-green-400');
                setOnRedirection(true);
                setDuration(1200);
            }
            if (error !== null) {
                setVisible("visible bg-red-400");
                setDuration(2500);
            }

            const timer = setTimeout(() => {
                setVisible('hidden');
                if (onRedirection && typeof onTimeOut === 'function') {
                    onTimeOut();
                }

            }, duration);

            return () => {
                clearTimeout(timer);

            } // Nettoyage du timer si le composant est démonté
        },
        [duration, error, message.length, onRedirection, onTimeOut]);

    return (
        <div
            className={`min-h-min  sm:h-1/6 w-full flex  justify-center items-center border border-gray-300 rounded-md shadow-sm ${visible} ${className}`}>{message} {error && error.message}</div>
    );

};

export default BoxMessage;