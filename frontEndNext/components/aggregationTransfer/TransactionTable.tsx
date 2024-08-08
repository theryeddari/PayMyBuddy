"use client";
// Props du composant TransactionTable
import {useAggregation} from "@/util/Context";
import {useEffect, useState} from "react";

interface TransactionTable {
    children?: React.ReactNode;
}

const TransactionTable: React.FC<TransactionTable> = ({children}) => {
    const {data, error, isLoading} = useAggregation(); // Utiliser le contexte
    const [listTransaction, setListTransaction] = useState<>([]);

    useEffect(() => {
        if (data?.transferredGeneralDetailResponse) {
            setListTransaction(data.transferredGeneralDetailResponse.listTransferredSuccesses);
        }
    }, [data]);
    const generateRows = () => {
        if (error) {
            return (
                <tr>
                    <td colSpan={3}
                        className="px-6 py-4 whitespace-nowrap font-bold text-gray-500 text-center bg-red-400">{error.message}</td>
                </tr>)

        } else {
            return listTransaction.map((transaction, index) => (
                <tr key={index}>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{transaction.receiverEmail}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{transaction.description}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{transaction.amount}</td>
                </tr>
            ));
        }
    };

    return (
        <div className={"w-full min-h-52 sm:h-1/3 m-1 overflow-y-auto border-4 bg-gray-50 "}>
            <span className={"text-l font-bold"}>Mes Transactions</span>
            <table className=" w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Relations</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Description</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Montant</th>
                </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                {generateRows()}
                </tbody>
                {children}
            </table>
        </div>
    );
};

export default TransactionTable;