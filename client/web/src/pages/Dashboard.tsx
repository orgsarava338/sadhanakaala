import useAuth from "@/auth/useAuth"

export default function Dashboard({}) {
    const { user, loading: userLoading } = useAuth();

    if (userLoading) return "User Loading...";

    return <main>
        <h1>Dashboard</h1>
        <div>{JSON.stringify(user)}</div>
    </main>
}