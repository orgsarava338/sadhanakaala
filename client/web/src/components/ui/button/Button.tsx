interface ButtonProps {
    disabled?: boolean;
    classaName?: string;
    onClick: React.MouseEventHandler<HTMLButtonElement>;
    children: React.ReactNode;
}

export default function Button({
    classaName = "px-6 py-3 rounded-lg bg-black text-white hover:opacity-90 flex items-center gap-3", 
    disabled = false, 
    onClick, 
    children
}: ButtonProps) {
    return <button onClick={onClick} disabled={disabled} className={classaName}>
     {children}
    </button>
}